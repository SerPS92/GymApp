package com.gymapp.api.dto.client.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Fields to partially update a client (PATCH). Only present fields are applied.")
public class ClientUpdateRequest {

    @Schema(description = "Client first name.", example = "Sergio")
    @Builder.Default
    private JsonNullable<String> firstName = JsonNullable.undefined();

    @Schema(description = "Client last name.", example = "Perez Garcia")
    @Builder.Default
    private JsonNullable<String> lastName = JsonNullable.undefined();

    @Schema(description = "Height in centimeters.", example = "181")
    @Builder.Default
    private JsonNullable<Integer> height = JsonNullable.undefined();

    @Schema(description = "Weight in kilograms.", example = "80")
    @Builder.Default
    private JsonNullable<Integer> weight = JsonNullable.undefined();

    @Schema(description = "Birth date (ISO-8601).", example = "1992-05-10")
    @Builder.Default
    private JsonNullable<LocalDate> birthDate = JsonNullable.undefined();
}

