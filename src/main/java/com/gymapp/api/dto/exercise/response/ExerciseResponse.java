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

    @Schema(description = "Public URL of the exercise image.", example = "https://cdn.gymapp.com/images/bench-press.jpg")
    String imageUrl;

    @Schema(description = "Original or generated image name.", example = "bench-press.jpg")
    String imageName;

    @Schema(description = "Internal storage path or key of the image.", example = "exercises/bench-press.jpg")
    String imagePath;
}
