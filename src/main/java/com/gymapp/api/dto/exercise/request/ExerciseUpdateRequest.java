package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Fields to partially update an exercise (PATCH). Only present fields are applied.")
public class ExerciseUpdateRequest {

    @Schema(description = "Exercise name.", example = "Barbell Bench Press")
    @Builder.Default
    private JsonNullable<String> name = JsonNullable.undefined();

    @Schema(description = "Exercise difficulty level.", example = "INTERMEDIATE")
    @Builder.Default
    private JsonNullable<Difficulty> difficulty = JsonNullable.undefined();

    @Schema(description = "Exercise description.", example = "Flat barbell bench press.")
    @Builder.Default
    private JsonNullable<String> description = JsonNullable.undefined();

    @Schema(description = "List of category IDs associated with this exercise.", example = "[1, 3, 5]")
    @Builder.Default
    private JsonNullable<List<Long>> categoryIds = JsonNullable.undefined();
}

