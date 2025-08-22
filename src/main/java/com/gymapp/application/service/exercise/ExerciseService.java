package com.gymapp.application.service.exercise;

import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.BadRequestException;
import org.springframework.data.domain.Pageable;

public interface ExerciseService {

    PageResponseDTO<ExerciseResponse> search(ExerciseFilterRequest filterRequest, Pageable pageable) throws BadRequestException;
}
