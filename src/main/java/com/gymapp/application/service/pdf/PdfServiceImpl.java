package com.gymapp.application.service.pdf;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.pdf.renderer.PdfCalendarRenderer;
import com.gymapp.application.pdf.util.PdfUtils;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ErrorCode;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static com.gymapp.shared.error.ErrorConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService{

    private final ExerciseJpaRepository exerciseJpaRepository;
    private final PdfCalendarRenderer pdfCalendarRenderer;

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
                case CALENDAR -> pdfCalendarRenderer.renderCalendar(document, programRequest, exerciseMap, true);
                case CALENDAR_NO_IMAGE -> pdfCalendarRenderer.renderCalendar(document, programRequest, exerciseMap, false);
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
                    ERROR_GENERATING_PDF_DOCUMENT, e
            );
        } catch (Exception e) {
            log.error("Unexpected error generating PDF: {}", e.getMessage());
            throw new AppException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    UNEXPECTED_ERROR_GENERATING_PDF_DOCUMENT, e
            );
        }
    }
}
