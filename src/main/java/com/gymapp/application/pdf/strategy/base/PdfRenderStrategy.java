package com.gymapp.application.pdf.strategy.base;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.domain.enums.PdfFormatType;
import com.lowagie.text.DocumentException;

public interface PdfRenderStrategy {
    boolean supports(PdfFormatType formatType);
    byte[] render(ProgramRequest programRequest) throws DocumentException;
}

