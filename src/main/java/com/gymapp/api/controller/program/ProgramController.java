package com.gymapp.api.controller.program;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.service.pdfservice.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProgramController implements ProgramApi{


    private final PdfService pdfService;

    @Override
    public ResponseEntity<Void> getPrograms() {
        return null;
    }

    @Override
    public ResponseEntity<Void> getProgramById(Long programId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createProgram(ProgramRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateProgram(Long programId, ProgramRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteProgram(Long programId) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> generateProgramPdf(@Valid ProgramRequest request) {
        byte[] pdfBytes = pdfService.generateProgramPdf(request);
        log.info("Pdf generated");
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=program.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
