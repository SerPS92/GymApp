package com.gymapp.application.pdf.strategy.calendar;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.pdf.dto.PdfExerciseDto;
import com.gymapp.application.pdf.renderer.PdfCalendarRenderer;
import com.gymapp.application.pdf.strategy.base.AbstractPdfRenderStrategy;
import com.gymapp.domain.enums.PdfFormatType;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CalendarRenderStrategy extends AbstractPdfRenderStrategy {

    private final PdfCalendarRenderer pdfCalendarRenderer;

    public CalendarRenderStrategy(
            ExerciseJpaRepository exerciseRepository,
            PdfCalendarRenderer pdfCalendarRenderer) {
        super(exerciseRepository);
        this.pdfCalendarRenderer = pdfCalendarRenderer;
    }

    @Override
    public boolean supports(PdfFormatType formatType) {
        return formatType == PdfFormatType.CALENDAR;
    }

    @Override
    protected void renderContent(
            Document document,
            ProgramRequest request,
            Map<Long, PdfExerciseDto> exerciseMap) throws DocumentException {

        pdfCalendarRenderer.renderCalendar(document, request, exerciseMap, true);
    }
}

