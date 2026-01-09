package com.gymapp.application.service.program;

import com.gymapp.api.dto.program.request.ProgramFilterRequest;
import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.program.response.ProgramResponse;
import com.gymapp.shared.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ProgramService {
    PageResponseDTO<ProgramResponse> search(ProgramFilterRequest programFilterRequest, Pageable pageable);
    ProgramResponse getById(Long id);
    ProgramResponse createProgram(ProgramRequest request);
    ProgramResponse updateProgram(Long programId, ProgramRequest request);
    void deleteProgram(Long id);
}
