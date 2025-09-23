package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Fields to partially update an exercise (PATCH). Only present fields are applied.")
public class ExerciseUpdateRequest {

    @Schema(description = "Exercise name.", example = "Barbell Bench Press")
    @Builder.Default
    private JsonNullable<String> name = JsonNullable.undefined();

    @Schema(description = "Primary muscle group.", example = "CHEST")
    @Builder.Default
    private JsonNullable<MuscleGroup> muscleGroup = JsonNullable.undefined();

    @Schema(description = "Exercise description.", example = "Flat barbell bench press.")
    @Builder.Default
    private JsonNullable<String> description = JsonNullable.undefined();
}

