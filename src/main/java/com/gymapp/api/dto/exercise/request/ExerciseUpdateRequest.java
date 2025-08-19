package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data to partially update an exercise.")
public class ExerciseUpdateRequest {
    private JsonNullable<String> name;
    private JsonNullable<MuscleGroup> muscleGroup;
    private JsonNullable<String> description;
}

