package com.gymapp.application.service.pdfservice;

import com.gymapp.api.dto.program.request.ProgramRequest;

public interface PdfService {

    byte[] generateProgramPdf(ProgramRequest programRequest);
}
