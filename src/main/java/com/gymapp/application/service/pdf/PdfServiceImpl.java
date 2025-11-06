package com.gymapp.application.service.pdf;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.service.pdf.factory.PdfExerciseCellFactory;
import com.gymapp.application.service.pdf.util.PdfUtils;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ErrorCode;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
            Map<Long, Exercise> exerciseMap =
                    PdfUtils.loadExercisesByIds(programRequest.getProgramExercises(), exerciseJpaRepository);

            Document document = PdfUtils.createDocument(out);
            PdfUtils.addHeader(document, programRequest);

            switch (programRequest.getPdfFormatType()) {
                case CALENDAR -> addCalendarTableGeneric(document, programRequest, exerciseMap, true);
                case CALENDAR_NO_IMAGE -> addCalendarTableGeneric(document, programRequest, exerciseMap, false);
                default -> throw new AppException(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.BAD_REQUEST,
                        UNSUPPORTED_PDF_FORMAT_TYPE + programRequest.getPdfFormatType()
                );
            }

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

    private void addCalendarTableGeneric(
            Document document,
            ProgramRequest request,
            Map<Long, Exercise> exerciseMap,
            boolean withImages) throws DocumentException {

        Map<String, List<ProgramExerciseRequest>> exercisesByDay =
                PdfUtils.groupExercisesByDay(document, request);

        if (exercisesByDay.isEmpty()) return;

        List<String> allDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        List<String> daysWithExercises = allDays.stream()
                .filter(day -> exercisesByDay.containsKey(day) && !exercisesByDay.get(day).isEmpty())
                .toList();

        int maxRows = daysWithExercises.stream()
                .mapToInt(day -> exercisesByDay.get(day).size())
                .max()
                .orElse(1);

        fillExerciseRowsGeneric(document, daysWithExercises, exercisesByDay, exerciseMap, maxRows, withImages);
    }

    private void fillExerciseRowsGeneric(
            Document document,
            List<String> daysWithExercises,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            int maxRows,
            boolean withImages) throws DocumentException {

        float[] config = withImages ? resolveLayoutConfig(maxRows) : resolveLayoutConfigNoImage(maxRows);
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
            addExerciseRow(currentTable, row, daysWithExercises, exercisesByDay, exerciseMap, layout);
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

    private void addExerciseRow(
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

                table.addCell(layout.withImages()
                        ? PdfExerciseCellFactory.createExerciseCell(exercise, ex, layout.imageSize(), layout.padding(), layout.fontSize())
                        : PdfExerciseCellFactory.createTextOnlyCell(exercise, ex, layout.padding(), layout.fontSize()));
            } else {
                table.addCell("");
            }
        }
    }

    private float[] resolveLayoutConfig(int maxRows) {
        if (maxRows <= 5) {
            return new float[]{70f, 6f, 10};
        } else {
            return new float[]{60f, 5f, 9};
        }
    }

    private float[] resolveLayoutConfigNoImage(int maxRows) {
        if (maxRows <= 6) return new float[]{6f, 10};
        if (maxRows <= 8) return new float[]{5f, 9};
        if (maxRows <= 10) return new float[]{4.5f, 8};
        return new float[]{3.5f, 7};
    }

    private PdfPTable createBaseTable(int columnCount) throws DocumentException {
        PdfPTable table = new PdfPTable(columnCount);
        table.setWidthPercentage(100);

        float[] widths = new float[columnCount];
        java.util.Arrays.fill(widths, 2f);
        table.setWidths(widths);

        return table;
    }

    private record PdfLayoutConfig(boolean withImages, float imageSize, float padding, int fontSize) {}

}
