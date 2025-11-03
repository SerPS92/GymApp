package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data to create a new exercise.")
public class ExerciseCreateRequest {

    @NotBlank(message = "name must not be blank")
    @Size(max = 50, message = "name must not exceed 50 characters")
    @Schema(description = "Exercise name.", example = "Bench Press")
    private String name;

    @NotNull(message = "difficulty must not be null")
    @Schema(description = "Difficulty level of the exercise.", example = "INTERMEDIATE")
    private Difficulty difficulty;

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    @Schema(description = "Exercise description.", example = "Flat barbell bench press focusing on pectorals.")
    private String description;

    @NotEmpty(message = "At least one category ID must be provided")
    @Schema(description = "List of category IDs associated with the exercise.", example = "[1, 3]")
    private List<Long> categoryIds;

    @Schema(description = "Optional public URL of the exercise image.", example = "/images/bench-press.jpg")
    private String imageUrl;
}

