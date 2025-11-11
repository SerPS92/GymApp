package com.gymapp.application.pdf.strategy.base;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.pdf.util.PdfDataUtils;
import com.gymapp.application.pdf.util.PdfUtils;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.PdfFormatType;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.ErrorCode;
import com.gymapp.shared.error.exception.AppException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static com.gymapp.shared.error.ErrorConstants.ERROR_GENERATING_PDF_DOCUMENT;

public abstract class AbstractPdfRenderStrategy implements PdfRenderStrategy {

    protected final ExerciseJpaRepository exerciseRepository;

    protected AbstractPdfRenderStrategy(ExerciseJpaRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public byte[] render(ProgramRequest request) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Map<Long, Exercise> exerciseMap =
                    PdfDataUtils.loadExercisesByIds(request.getProgramExercises(), exerciseRepository);

            Document document = PdfUtils.createDocument(out, request.getPdfFormatType());

            if (request.getPdfFormatType() == PdfFormatType.LIST) {
                PdfUtils.addListHeader(document, request);
            } else {
                PdfUtils.addHeader(document, request);
            }

            renderContent(document, request, exerciseMap);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new AppException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    ERROR_GENERATING_PDF_DOCUMENT, e
            );
        }
    }

    protected abstract void renderContent(
            Document document,
            ProgramRequest request,
            Map<Long, Exercise> exerciseMap
    ) throws DocumentException;
}
