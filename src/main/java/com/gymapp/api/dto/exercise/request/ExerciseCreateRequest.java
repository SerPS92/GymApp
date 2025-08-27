package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data to create a new exercise.")
public class ExerciseCreateRequest {

    @NotBlank(message = "name must not be blank")
    @Schema(description = "Exercise name.", example = "Bench Press")
    private String name;

    @NotNull(message = "muscleGroup must not be null")
    @Schema(description = "Primary muscle group (enum/string).", example = "CHEST")
    private MuscleGroup muscleGroup;

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    @Schema(description = "Exercise description.", example = "Flat barbell bench press focusing on pectorals.")
    private String description;
}

