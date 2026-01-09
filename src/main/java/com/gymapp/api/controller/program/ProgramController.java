package com.gymapp.api.controller.program;

import com.gymapp.api.dto.program.request.ProgramFilterRequest;
import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.program.response.ProgramResponse;
import com.gymapp.application.pdf.util.PdfFileNameGenerator;
import com.gymapp.application.service.pdf.PdfService;
import com.gymapp.application.service.program.ProgramService;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProgramController implements ProgramApi{

    private final ProgramService programService;
    private final PdfService pdfService;

    @Override
    public ResponseEntity<PageResponseDTO<ProgramResponse>> getPrograms(
            @Valid @ModelAttribute ProgramFilterRequest filter,
            @ParameterObject Pageable pageable) throws BadRequestException {
        PageResponseDTO<ProgramResponse> responseDTO = programService.search(filter, pageable);
        log.info("Content size: {}", responseDTO.getContent().size());
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ProgramResponse> getProgramById(Long programId) {
        ProgramResponse response = programService.getById(programId);
        log.info("Program found with id: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProgramResponse> createProgram(ProgramRequest request) {
        ProgramResponse created = programService.createProgram(request);

        java.net.URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        log.info("Program created: {}", created.getTitle());
        return ResponseEntity.created(location).body(created);
    }

    @Override
    public ResponseEntity<ProgramResponse> updateProgram(Long programId, ProgramRequest request) {
        ProgramResponse response = programService.updateProgram(programId, request);
        log.info("Program with Id: {} is updated", response.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteProgram(Long programId) {
        programService.deleteProgram(programId);
        log.info("Program deleted with id: {}", programId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<byte[]> generateProgramPdf(@Valid ProgramRequest request) {
        byte[] pdfBytes = pdfService.generateProgramPdf(request);
        String fileName = PdfFileNameGenerator.generateFileName(request);
        log.info("Pdf generated: {}", fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(fileName)
                                .build()
                                .toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);

    }
}
