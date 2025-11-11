package com.gymapp.application.pdf.strategy.list;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.pdf.renderer.PdfListRenderer;
import com.gymapp.application.pdf.strategy.base.AbstractPdfRenderStrategy;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.PdfFormatType;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ListRenderStrategy extends AbstractPdfRenderStrategy {

    private final PdfListRenderer pdfListRenderer;

    public ListRenderStrategy(ExerciseJpaRepository exerciseRepository,
                              PdfListRenderer pdfListRenderer) {
        super(exerciseRepository);
        this.pdfListRenderer = pdfListRenderer;
    }

    @Override
    public boolean supports(PdfFormatType formatType) {
        return formatType == PdfFormatType.LIST;
    }

    @Override
    protected void renderContent(Document document,
                                 ProgramRequest request,
                                 Map<Long, Exercise> exerciseMap) throws DocumentException {
        pdfListRenderer.renderList(document, request, exerciseMap);
    }
}
