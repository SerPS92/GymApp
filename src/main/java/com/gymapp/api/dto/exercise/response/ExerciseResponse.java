package com.gymapp.api.dto.exercise.response;

import com.gymapp.domain.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Schema(description = "Represents an exercise.")
public class ExerciseResponse {

    @Schema(description = "Unique exercise ID.", example = "1")
    Long id;

    @Schema(description = "Exercise name.", example = "Bench Press")
    String name;

    @Schema(description = "Primary muscle group.", example = "CHEST")
    MuscleGroup muscleGroup;

    @Schema(description = "Exercise description.", example = "Flat barbell bench press focusing on pectorals.")
    String description;
}
