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

import static com.gymapp.shared.error.ErrorConstants.ERROR_GENERATING_PDF_DOCUMENT;
import static com.gymapp.shared.error.ErrorConstants.UNEXPECTED_ERROR_GENERATING_PDF_DOCUMENT;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService{

    private final ExerciseJpaRepository exerciseJpaRepository;

    @Override
    public byte[] generateProgramPdf(ProgramRequest programRequest) {
        return switch (programRequest.getPdfFormatType()) {
            case CALENDAR -> generateCalendarPdf(programRequest);
            case CALENDAR_NO_IMAGE -> generateCalendarNoImagePdf(programRequest);
            default ->
                    throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, "Unsupported PDF format type");
        };
    }

    private byte[] generateCalendarPdf(ProgramRequest programRequest){
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Map<Long, Exercise> exerciseMap =
                    PdfUtils.loadExercisesByIds(programRequest.getProgramExercises(), exerciseJpaRepository);

            Document document = PdfUtils.createDocument(out);
            PdfUtils.addHeader(document, programRequest);

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

    private byte[] generateCalendarNoImagePdf(ProgramRequest programRequest) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Map<Long, Exercise> exerciseMap =
                    PdfUtils.loadExercisesByIds(programRequest.getProgramExercises(), exerciseJpaRepository);

            Document document = PdfUtils.createDocument(out);
            PdfUtils.addHeader(document, programRequest);

            addCalendarTableWithoutImages(document, programRequest, exerciseMap);

            document.close();
            return out.toByteArray();

        } catch (DocumentException e) {
            log.error("Error generating PDF (no image): {}", e.getMessage());
            throw new AppException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    ERROR_GENERATING_PDF_DOCUMENT,
                    e
            );
        } catch (Exception e) {
            log.error("Unexpected error generating PDF (no image): {}", e.getMessage());
            throw new AppException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    UNEXPECTED_ERROR_GENERATING_PDF_DOCUMENT,
                    e
            );
        }
    }


    private void addCalendarTable(Document document, ProgramRequest request, Map<Long, Exercise> exerciseMap)
            throws DocumentException {

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

        fillExerciseRows(document, daysWithExercises, exercisesByDay, exerciseMap, maxRows);
    }

    private void addCalendarTableWithoutImages(Document document, ProgramRequest request, Map<Long, Exercise> exerciseMap)
            throws DocumentException {

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

        fillTextOnlyRows(document, daysWithExercises, exercisesByDay, exerciseMap, maxRows);
    }



    private void fillExerciseRows(
            Document document,
            List<String> daysWithExercises,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            int maxRows) throws DocumentException {

        float[] config = resolveLayoutConfig(maxRows);
        float imageSize = config[0];
        float padding = config[1];
        int fontSize = (int) config[2];

        int rowsPerPage = 6;
        int pageNumber = 1;
        int totalPages = (int) Math.ceil((double) maxRows / rowsPerPage);

        PdfPTable currentTable = createBaseTable(daysWithExercises.size());
        PdfUtils.addDayHeaders(currentTable, daysWithExercises);

        int currentRowCount = 0;

        for (int row = 1; row <= maxRows; row++) {
            for (String day : daysWithExercises) {
                List<ProgramExerciseRequest> exercises =
                        exercisesByDay.getOrDefault(day, Collections.emptyList());
                exercises.sort(Comparator.comparing(ProgramExerciseRequest::getPosition));

                if (exercises.size() >= row) {
                    ProgramExerciseRequest ex = exercises.get(row - 1);
                    Exercise exercise = exerciseMap.get(ex.getExerciseId());
                    currentTable.addCell(PdfExerciseCellFactory.createExerciseCell(
                            exercise, ex, imageSize, padding, fontSize
                    ));
                } else {
                    currentTable.addCell("");
                }
            }

            currentRowCount++;

            if (currentRowCount == rowsPerPage && row < maxRows) {
                document.add(currentTable);
                PdfUtils.addPageIndicator(document, pageNumber, totalPages);
                document.newPage();

                pageNumber++;
                currentTable = createBaseTable(daysWithExercises.size());
                PdfUtils.addDayHeaders(currentTable, daysWithExercises);
                currentRowCount = 0;
            }
        }

        if (currentRowCount > 0) {
            document.add(currentTable);
            PdfUtils.addPageIndicator(document, pageNumber, totalPages);
        }
    }

    private void fillTextOnlyRows(
            Document document,
            List<String> daysWithExercises,
            Map<String, List<ProgramExerciseRequest>> exercisesByDay,
            Map<Long, Exercise> exerciseMap,
            int maxRows) throws DocumentException {

        float[] config = resolveLayoutConfigNoImage(maxRows);
        float padding = config[0];
        int fontSize = (int) config[1];
        int rowsPerPage = 10;


        int pageNumber = 1;
        int totalPages = (int) Math.ceil((double) maxRows / rowsPerPage);

        PdfPTable currentTable = createBaseTable(daysWithExercises.size());
        PdfUtils.addDayHeaders(currentTable, daysWithExercises);

        int currentRowCount = 0;

        for (int row = 1; row <= maxRows; row++) {
            for (String day : daysWithExercises) {
                List<ProgramExerciseRequest> exercises =
                        exercisesByDay.getOrDefault(day, Collections.emptyList());
                exercises.sort(Comparator.comparing(ProgramExerciseRequest::getPosition));

                if (exercises.size() >= row) {
                    ProgramExerciseRequest ex = exercises.get(row - 1);
                    Exercise exercise = exerciseMap.get(ex.getExerciseId());
                    currentTable.addCell(PdfExerciseCellFactory.createTextOnlyCell(exercise, ex, padding, fontSize));
                } else {
                    currentTable.addCell("");
                }
            }

            currentRowCount++;

            if (currentRowCount == rowsPerPage && row < maxRows) {
                document.add(currentTable);
                PdfUtils.addPageIndicator(document, pageNumber, totalPages);
                document.newPage();

                pageNumber++;
                currentTable = createBaseTable(daysWithExercises.size());
                PdfUtils.addDayHeaders(currentTable, daysWithExercises);
                currentRowCount = 0;
            }
        }

        if (currentRowCount > 0) {
            document.add(currentTable);
            PdfUtils.addPageIndicator(document, pageNumber, totalPages);
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

}
