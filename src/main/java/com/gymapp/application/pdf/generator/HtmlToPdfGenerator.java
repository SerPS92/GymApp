package com.gymapp.application.pdf.generator;

import com.gymapp.application.pdf.viewmodel.PdfProgramViewModel;
import com.gymapp.shared.error.ErrorCode;
import com.gymapp.shared.error.ErrorConstants;
import com.gymapp.shared.error.exception.AppException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HtmlToPdfGenerator {

    private final TemplateEngine templateEngine;

    public byte[] generate(PdfProgramViewModel viewModel) {

        Context context = new Context();
        context.setVariables(
                Map.of(
                        "title", viewModel.getTitle(),
                        "startDate", viewModel.getStartDate(),
                        "endDate", viewModel.getEndDate(),
                        "dayLabels", viewModel.getDayLabels(),
                        "notes", viewModel.getNotes(),
                        "exercisesByDay", viewModel.getExercisesByDay()
                )
        );

        String html = templateEngine.process(
                "pdf/templates/program",
                context
        );

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.PDF_GENERATION_ERROR,
                    ErrorConstants.ERROR_GENERATING_PDF_DOCUMENT,
                    e);
        }
    }
}
