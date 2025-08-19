package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseFilterRequest {

    @Parameter(description = "Filter by name (partial, case-insensitive).")
    private String name;

    @Parameter(description = "Filter by muscle group.")
    private MuscleGroup muscleGroup;
}

