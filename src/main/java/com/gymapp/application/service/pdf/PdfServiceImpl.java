package com.gymapp.application.service.pdf;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ErrorCode;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gymapp.shared.error.ErrorConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService{


    private final ExerciseJpaRepository exerciseJpaRepository;


    @Override
    public byte[] generateProgramPdf(ProgramRequest programRequest) {
        return generateCalendarPdf(programRequest);
    }

    private byte[] generateCalendarPdf(ProgramRequest programRequest){
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Map<Long, Exercise> exerciseMap = loadExercisesByIds(programRequest.getProgramExercises());

            Document document = createDocument(out);
            addHeader(document, programRequest);
            addCalendarTable(document, programRequest, exerciseMap);

            document.close();
            return out.toByteArray();

        } catch (DocumentException e) {
            log.error("Error generating PDF: {}", e.getMessage());
            throw new AppException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    ERROR_GENERATING_PDF_DOCUMENT,
                    e
            );
        } catch (Exception e) {
            log.error("Unexpected error generating PDF: {}", e.getMessage());
            throw new AppException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    UNEXPECTED_ERROR_GENERATING_PDF_DOCUMENT,
                    e
            );
        }
    }

    private Map<Long, Exercise> loadExercisesByIds(List<ProgramExerciseRequest> requests) {
        List<Long> exerciseIds = requests.stream()
                .map(ProgramExerciseRequest::getExerciseId)
                .distinct()
                .toList();

        List<Exercise> exercises = exerciseJpaRepository.findAllById(exerciseIds);

        if (exercises.size() != exerciseIds.size()) {
            List<Long> foundIds = exercises.stream().map(Exercise::getId).toList();
            List<Long> missing = exerciseIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new AppException(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NOT_FOUND,
                    EXERCISES_NOT_FOUND_WITH_IDS + missing
            );
        }
        log.info("Found {} exercises", exercises.size());
        return exercises.stream()
                .collect(Collectors.toMap(Exercise::getId, e -> e));
    }

    private Document createDocument(ByteArrayOutputStream out) throws DocumentException {
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 10, 20);
        PdfWriter.getInstance(document, out);
        document.open();
        return document;
    }

    private void addHeader(Document document, ProgramRequest request) throws DocumentException {
        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font dateFont = new Font(Font.HELVETICA, 11);

        String titleText = (request.getTitle() != null && !request.getTitle().isBlank())
                ? request.getTitle()
                : "Training Program";

        String dateText = String.format("From %s to %s", request.getStartDate(), request.getEndDate());

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3f, 2f});

        PdfPCell titleCell = new PdfPCell(new Phrase(titleText, titleFont));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell dateCell = new PdfPCell(new Phrase(dateText, dateFont));
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        headerTable.addCell(titleCell);
        headerTable.addCell(dateCell);

        document.add(headerTable);

        Paragraph spacer = new Paragraph(" ");
        spacer.setSpacingAfter(6f);
        document.add(spacer);
    }


    private void addCalendarTable(Document document, ProgramRequest request, Map<Long, Exercise> exerciseMap)
            throws DocumentException {

        // Agrupar ejercicios por día
        Map<String, List<ProgramExerciseRequest>> exercisesByDay = request.getProgramExercises().stream()
                .collect(Collectors.groupingBy(ProgramExerciseRequest::getDay));

        // Lista completa de días en orden
        List<String> allDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        // Filtrar días que tienen ejercicios
        List<String> daysWithExercises = allDays.stream()
                .filter(day -> exercisesByDay.containsKey(day) && !exercisesByDay.get(day).isEmpty())
                .toList();

        if (daysWithExercises.isEmpty()) {
            Paragraph emptyMsg = new Paragraph("No exercises available for this program.",
                    new Font(Font.HELVETICA, 12, Font.ITALIC, Color.GRAY));
            emptyMsg.setAlignment(Element.ALIGN_CENTER);
            document.add(emptyMsg);
            return;
        }

        // Calcular filas máximas
        int maxRows = daysWithExercises.stream()
                .mapToInt(day -> exercisesByDay.get(day).size())
                .max()
                .orElse(1);

        // Generar las filas
        fillExerciseRows(document, daysWithExercises, exercisesByDay, exerciseMap, maxRows);
    }

    private void addDayHeaders(PdfPTable table, List<String> daysOfWeek) {
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        for (String day : daysOfWeek) {
            PdfPCell header = new PdfPCell(new Phrase(day, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setPadding(6f);
            table.addCell(header);
        }
    }

    private void fillExerciseRows(
            Document document,
            List<String> daysWithExercises,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            int maxRows) throws DocumentException {

        float imageSize;
        float padding;
        int fontSize;

        if (maxRows <= 5) {
            imageSize = 70f;
            padding = 6f;
            fontSize = 10;
        } else {
            imageSize = 60f;
            padding = 5f;
            fontSize = 9;
        }

        int rowsPerPage = 6;
        int pageNumber = 1;
        int totalPages = (int) Math.ceil((double) maxRows / rowsPerPage);

        PdfPTable currentTable = new PdfPTable(daysWithExercises.size());
        currentTable.setWidthPercentage(100);

        float[] widths = new float[daysWithExercises.size()];
        for (int i = 0; i < widths.length; i++) widths[i] = 2f;
        currentTable.setWidths(widths);

        addDayHeaders(currentTable, daysWithExercises);

        int currentRowCount = 0;

        for (int row = 1; row <= maxRows; row++) {
            for (String day : daysWithExercises) {
                List<ProgramExerciseRequest> exercises =
                        exercisesByDay.getOrDefault(day, Collections.emptyList());
                exercises.sort(Comparator.comparing(ProgramExerciseRequest::getPosition));

                if (exercises.size() >= row) {
                    ProgramExerciseRequest ex = exercises.get(row - 1);
                    Exercise exercise = exerciseMap.get(ex.getExerciseId());
                    currentTable.addCell(createExerciseCell(exercise, ex, imageSize, padding, fontSize));
                } else {
                    currentTable.addCell("");
                }
            }

            currentRowCount++;

            if (currentRowCount == rowsPerPage && row < maxRows) {
                document.add(currentTable);

                Paragraph pageIndicator = new Paragraph(
                        String.format("Page %d / %d", pageNumber, totalPages),
                        new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY)
                );
                pageIndicator.setAlignment(Element.ALIGN_RIGHT);
                pageIndicator.setSpacingBefore(6f);
                document.add(pageIndicator);

                document.newPage();
                pageNumber++;

                currentTable = new PdfPTable(daysWithExercises.size());
                currentTable.setWidthPercentage(100);
                currentTable.setWidths(widths);

                addDayHeaders(currentTable, daysWithExercises);
                currentRowCount = 0;
            }
        }

        if (currentRowCount > 0) {
            document.add(currentTable);

            Paragraph pageIndicator = new Paragraph(
                    String.format("Page %d / %d", pageNumber, totalPages),
                    new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY)
            );
            pageIndicator.setAlignment(Element.ALIGN_RIGHT);
            pageIndicator.setSpacingBefore(6f);
            document.add(pageIndicator);
        }
    }

    private PdfPCell createExerciseCell(
            Exercise exercise,
            ProgramExerciseRequest ex,
            float imageSize,
            float padding,
            int fontSize) {

        PdfPCell cell = new PdfPCell();
        cell.setPadding(padding);

        if (exercise.getImageUrl() != null && !exercise.getImageUrl().isBlank()) {
            try {
                Image img = Image.getInstance("src/main/resources/static" + exercise.getImageUrl());
                img.scaleAbsolute(imageSize, imageSize); // 👈 fuerza tamaño fijo
                img.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(img);
            } catch (Exception e) {
                log.warn("Could not load image for exercise {}: {}", exercise.getName(), e.getMessage());
            }
        }


        String sets = ex.getSets() != null ? ex.getSets() : "-";
        String reps = ex.getReps() != null ? ex.getReps() : "-";
        String rest = ex.getRestTime() != null && !ex.getRestTime().isBlank()
                ? ex.getRestTime().replace("\"", "") + "\""
                : "";
        String notes = (ex.getNotes() != null && !ex.getNotes().isBlank()) ? ex.getNotes() : "";

        Font nameFont = new Font(Font.HELVETICA, fontSize - 1, Font.BOLD);
        Font dataFont = new Font(Font.HELVETICA, fontSize - 1);
        Font noteFont = new Font(Font.HELVETICA, fontSize - 1, Font.ITALIC, new Color(120, 120, 120)); // gris suave

        Chunk nameChunk = new Chunk(exercise.getName(), nameFont);
        Chunk dataChunk = new Chunk(" " + sets + "x" + reps + " " + rest, dataFont);
        Chunk noteChunk = notes.isEmpty() ? new Chunk("") : new Chunk(" " + notes, noteFont);

        Phrase phrase = new Phrase();
        phrase.add(nameChunk);
        phrase.add(dataChunk);
        phrase.add(noteChunk);

        Paragraph paragraph = new Paragraph(phrase);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setLeading(11f);
        paragraph.setSpacingBefore(2f);

        cell.addElement(paragraph);
        return cell;
    }
    
}
