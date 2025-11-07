package com.gymapp.application.pdf.renderer;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.pdf.factory.PdfExerciseCellFactory;
import com.gymapp.application.pdf.model.PdfLayoutConfig;
import com.gymapp.application.pdf.util.PdfDataUtils;
import com.gymapp.application.pdf.util.PdfUtils;
import com.gymapp.domain.entity.Exercise;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class PdfCalendarRenderer {

    public void renderCalendar(
            Document document,
            ProgramRequest request,
            Map<Long, Exercise> exerciseMap,
            boolean withImages) throws DocumentException {

        Map<String, List<ProgramExerciseRequest>> exercisesByDay =
                PdfDataUtils.groupExercisesByDay(document, request);

        if (exercisesByDay.isEmpty()) return;

        List<String> allDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        List<String> daysWithExercises = allDays.stream()
                .filter(day -> exercisesByDay.containsKey(day) && !exercisesByDay.get(day).isEmpty())
                .toList();

        int maxRows = daysWithExercises.stream()
                .mapToInt(day -> exercisesByDay.get(day).size())
                .max()
                .orElse(1);

        fillRows(document, daysWithExercises, exercisesByDay, exerciseMap, maxRows, withImages);
    }

    private void fillRows(
            Document document,
            List<String> daysWithExercises,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            int maxRows,
            boolean withImages) throws DocumentException {

        float[] config = withImages ? PdfUtils.resolveLayoutConfig(maxRows) : PdfUtils.resolveLayoutConfigNoImage(maxRows);
        PdfLayoutConfig layout = withImages
                ? new PdfLayoutConfig(true, config[0], config[1], (int) config[2])
                : new PdfLayoutConfig(false, 0, config[0], (int) config[1]);

        int rowsPerPage = withImages ? 6 : 10;
        int pageNumber = 1;
        int totalPages = (int) Math.ceil((double) maxRows / rowsPerPage);

        PdfPTable currentTable = createBaseTable(daysWithExercises.size());
        PdfUtils.addDayHeaders(currentTable, daysWithExercises);

        int currentRowCount = 0;

        for (int row = 1; row <= maxRows; row++) {
            addRow(currentTable, row, daysWithExercises, exercisesByDay, exerciseMap, layout);
            currentRowCount++;

            if (currentRowCount == rowsPerPage && row < maxRows) {
                PdfUtils.finalizePage(document, currentTable, pageNumber, totalPages);
                pageNumber++;
                currentTable = createBaseTable(daysWithExercises.size());
                PdfUtils.addDayHeaders(currentTable, daysWithExercises);
                currentRowCount = 0;
            }
        }

        if (currentRowCount > 0) {
            PdfUtils.finalizePage(document, currentTable, pageNumber, totalPages);
        }
    }

    private void addRow(
            PdfPTable table,
            int row,
            List<String> daysWithExercises,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            PdfLayoutConfig layout) {

        for (String day : daysWithExercises) {
            List<ProgramExerciseRequest> exercises = exercisesByDay.getOrDefault(day, Collections.emptyList());
            exercises.sort(Comparator.comparing(ProgramExerciseRequest::getPosition));

            if (exercises.size() >= row) {
                ProgramExerciseRequest ex = exercises.get(row - 1);
                Exercise exercise = exerciseMap.get(ex.getExerciseId());

                table.addCell(layout.isWithImages()
                        ? PdfExerciseCellFactory.createExerciseCell(exercise, ex, layout.getImageSize(), layout.getPadding(), layout.getFontSize())
                        : PdfExerciseCellFactory.createTextOnlyCell(exercise, ex, layout.getPadding(), layout.getFontSize()));
            } else {
                table.addCell("");
            }
        }
    }

    private PdfPTable createBaseTable(int columnCount) throws DocumentException {
        PdfPTable table = new PdfPTable(columnCount);
        table.setWidthPercentage(100);

        float[] widths = new float[columnCount];
        java.util.Arrays.fill(widths, 2f);
        table.setWidths(widths);

        return table;
    }
}
