package com.gymapp.api.controller.exercise;


import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.application.service.exercise.ExerciseService;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.BadRequestException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ExerciseController implements ExerciseApi{

    private final ExerciseService exerciseService;

    @Override
    public ResponseEntity<PageResponseDTO<ExerciseResponse>> getExercises(
            @Valid ExerciseFilterRequest filter, int page, int size) throws BadRequestException {
        Pageable pageable = PageRequest.of(page, size);
        PageResponseDTO<ExerciseResponse> responseDTO = exerciseService.search(filter, pageable);
        log.info("Content size: {}", responseDTO.getContent().size());
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ExerciseResponse> createExercise(ExerciseCreateRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseResponse> getExerciseById(Long exerciseId) {
        ExerciseResponse response = exerciseService.getById(exerciseId);
        log.info("Exercise found: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ExerciseResponse> updateExercise(Long exerciseId, ExerciseUpdateRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteExercise(Long exerciseId) {
        return null;
    }
}
