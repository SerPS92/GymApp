package com.gymapp.application.pdf.strategy.list;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.pdf.strategy.base.AbstractPdfRenderStrategy;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.PdfFormatType;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

import java.util.Map;

public class ListRenderStrategy extends AbstractPdfRenderStrategy {

    public ListRenderStrategy(ExerciseJpaRepository exerciseJpaRepository){
        super(exerciseJpaRepository);
    }

    @Override
    protected void renderContent(Document document, ProgramRequest request, Map<Long, Exercise> exerciseMap) throws DocumentException {

    }

    @Override
    public boolean supports(PdfFormatType formatType) {
        return formatType == PdfFormatType.LIST;
    }
}
