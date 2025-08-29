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
@Schema(description = "Campos a actualizar parcialmente en un ejercicio (PATCH). Solo se aplican los presentes.")
public class ExerciseUpdateRequest {

    @Schema(description = "Nombre del ejercicio", example = "Barbell Bench Press")
    @Builder.Default
    private JsonNullable<String> name = JsonNullable.undefined();

    @Schema(description = "Grupo muscular principal", example = "CHEST")
    @Builder.Default
    private JsonNullable<MuscleGroup> muscleGroup = JsonNullable.undefined();

    @Schema(description = "Descripción del ejercicio", example = "Press de banca plano con barra.")
    @Builder.Default
    private JsonNullable<String> description = JsonNullable.undefined();
}

