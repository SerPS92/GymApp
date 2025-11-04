package com.gymapp.application.service.pdfservice;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ErrorCode;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
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
        String titleText = request.getTitle() != null && !request.getTitle().isBlank()
                ? request.getTitle()
                : "Training Program";

        Paragraph title = new Paragraph(titleText, new Font(Font.HELVETICA, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph dates = new Paragraph(
                String.format("From %s to %s", request.getStartDate(), request.getEndDate()),
                new Font(Font.HELVETICA, 12)
        );
        dates.setAlignment(Element.ALIGN_CENTER);
        document.add(dates);

        Paragraph spacer = new Paragraph(" ");
        spacer.setSpacingAfter(4f);
        document.add(spacer);
    }

    private void addCalendarTable(Document document, ProgramRequest request, Map<Long, Exercise> exerciseMap)
            throws DocumentException {

        Map<String, List<ProgramExerciseRequest>> exercisesByDay = request.getProgramExercises().stream()
                .collect(Collectors.groupingBy(ProgramExerciseRequest::getDay));

        List<String> daysOfWeek = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2f, 2f, 2f, 2f, 2f, 2f, 2f});

        addDayHeaders(table, daysOfWeek);

        int maxRows = exercisesByDay.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(1);

        fillExerciseRows(table, daysOfWeek, exercisesByDay, exerciseMap, maxRows);

        document.add(table);
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
            PdfPTable table,
            List<String> daysOfWeek,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            int maxRows) {

        float imageSize;
        float padding;
        int fontSize;

        if (maxRows <= 5) {
            imageSize = 70f;
            padding = 6f;
            fontSize = 10;
        } else if (maxRows == 6) {
            imageSize = 60f;
            padding = 5f;
            fontSize = 9;
        } else if (maxRows == 7) {
            imageSize = 50f;
            padding = 4f;
            fontSize = 8;
        } else {
            imageSize = 45f;
            padding = 3f;
            fontSize = 8;
        }

        for (int row = 1; row <= maxRows; row++) {
            for (String day : daysOfWeek) {
                List<ProgramExerciseRequest> exercises = exercisesByDay.getOrDefault(day, Collections.emptyList());
                exercises.sort(Comparator.comparing(ProgramExerciseRequest::getPosition));

                if (exercises.size() >= row) {
                    ProgramExerciseRequest ex = exercises.get(row - 1);
                    Exercise exercise = exerciseMap.get(ex.getExerciseId());
                    table.addCell(createExerciseCell(exercise, ex, imageSize, padding, fontSize));
                } else {
                    table.addCell("");
                }
            }
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
                img.scaleToFit(imageSize, imageSize);
                img.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(img);
            } catch (Exception e) {
                log.warn("Could not load image for exercise {}: {}", exercise.getName(), e.getMessage());
            }
        }

        String line = formatExerciseLine(ex);
        String content = exercise.getName() + "\n" + line;

        Paragraph paragraph = new Paragraph(content, new Font(Font.HELVETICA, fontSize));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        return cell;
    }

    private String formatExerciseLine(ProgramExerciseRequest ex) {
        String rest = ex.getRestTime() != null && !ex.getRestTime().isBlank()
                ? ex.getRestTime().replace("\"", "") + "\""
                : "";

        String sets = ex.getSets() != null ? ex.getSets() : "-";
        String reps = ex.getReps() != null ? ex.getReps() : "-";

        return rest.isEmpty()
                ? String.format("%sx%s", sets, reps)
                : String.format("%sx%s - %s", sets, reps, rest);
    }
    
}
