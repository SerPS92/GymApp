package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseFilterRequest {

    @Parameter(description = "Filter by name (partial, case-insensitive).")
    @Size(max = 50)
    private String name;

    @Parameter(description = "Filter by muscle group.")
    private MuscleGroup muscleGroup;
}

