package com.gymapp.api.controller.exercise;


import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.shared.dto.PageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ExerciseController implements ExerciseApi{

    @Override
    public ResponseEntity<PageResponseDTO<ExerciseResponse>> getExercises(ExerciseFilterRequest filter, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseResponse> createExercise(ExerciseCreateRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseResponse> getExerciseById(Long exerciseId) {
        return null;
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
