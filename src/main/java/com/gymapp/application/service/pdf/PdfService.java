package com.gymapp.application.service.pdf;

import com.gymapp.api.dto.program.request.ProgramRequest;

public interface PdfService {

    byte[] generateProgramPdf(ProgramRequest programRequest);
}
