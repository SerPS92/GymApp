package com.gymapp.api.dto.exercise.response;

import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.domain.enums.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@Schema(description = "Represents an exercise.")
public class ExerciseResponse {

    @Schema(description = "Unique exercise ID.", example = "1")
    Long id;

    @Schema(description = "Exercise name.", example = "Bench Press")
    String name;

    @Schema(description = "Difficulty level of the exercise.", example = "BEGINNER")
    Difficulty difficulty;

    @Schema(description = "Exercise description.", example = "Flat barbell bench press focusing on pectorals.")
    String description;

    @Schema(description = "List of associated categories.")
    List<CategoryResponse> categories;
}
