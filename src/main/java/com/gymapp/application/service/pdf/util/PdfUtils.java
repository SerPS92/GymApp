package com.gymapp.application.service.pdf.util;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ErrorCode;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gymapp.shared.error.ErrorConstants.EXERCISES_NOT_FOUND_WITH_IDS;

@Slf4j
public class PdfUtils {

    private PdfUtils() {}

    public static Document createDocument(ByteArrayOutputStream out) throws DocumentException {
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 10, 20);
        PdfWriter.getInstance(document, out);
        document.open();
        return document;
    }

    public static void addHeader(Document document, ProgramRequest request) throws DocumentException {
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


    public static Map<Long, Exercise> loadExercisesByIds(
            List<ProgramExerciseRequest> requests,
            ExerciseJpaRepository exerciseRepository) {

        List<Long> exerciseIds = requests.stream()
                .map(ProgramExerciseRequest::getExerciseId)
                .distinct()
                .toList();

        List<Exercise> exercises = exerciseRepository.findAllById(exerciseIds);

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


    public static void addPageIndicator(Document document, int pageNumber, int totalPages) throws DocumentException {
        Paragraph pageIndicator = new Paragraph(
                String.format("Page %d / %d", pageNumber, totalPages),
                new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY)
        );
        pageIndicator.setAlignment(Element.ALIGN_RIGHT);
        pageIndicator.setSpacingBefore(6f);
        document.add(pageIndicator);
    }

    public static void addDayHeaders(PdfPTable table, List<String> daysOfWeek) {
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        for (String day : daysOfWeek) {
            PdfPCell header = new PdfPCell(new Phrase(day, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setPadding(6f);
            table.addCell(header);
        }
    }

    public static Map<String, List<ProgramExerciseRequest>> groupExercisesByDay(
            Document document,
            ProgramRequest request) throws DocumentException {

        Map<String, List<ProgramExerciseRequest>> exercisesByDay = request.getProgramExercises().stream()
                .collect(Collectors.groupingBy(ProgramExerciseRequest::getDay));

        List<String> allDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        List<String> daysWithExercises = allDays.stream()
                .filter(day -> exercisesByDay.containsKey(day) && !exercisesByDay.get(day).isEmpty())
                .toList();

        if (daysWithExercises.isEmpty()) {
            Paragraph emptyMsg = new Paragraph("No exercises available for this program.",
                    new Font(Font.HELVETICA, 12, Font.ITALIC, Color.GRAY));
            emptyMsg.setAlignment(Element.ALIGN_CENTER);
            document.add(emptyMsg);
            return Map.of();
        }

        return exercisesByDay;
    }


}
