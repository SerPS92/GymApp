package com.gymapp.api.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Schema(description = "Represents an user data. ")
@Builder
public class UserResponse {

    @Schema(description = "Unique user ID.", example = "1")
    Long id;

    @Schema(description = "User first name.", example = "Sergio")
    String firstName;

    @Schema(description = "User last name.", example = "Perez Garcia")
    String lastName;

    @Schema(description = "Height in centimeters.", example = "180")
    Integer height;

    @Schema(description = "Weight in kilograms.", example = "78")
    Integer weight;

    @Schema(description = "Birth date (ISO-8601).", example = "1992-05-10")
    LocalDate birthDate;
}
