package com.gymapp.api.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data to create a new user.")
public class UserCreateRequest {

    @NotBlank(message = "firstName must not be blank")
    @Size(max = 60, message = "firstName must not exceed 60 characters")
    @Schema(description = "User first name.", example = "Sergio")
    private String firstName;

    @NotBlank(message = "lastName must not be blank")
    @Size(max = 60, message = "lastName must not exceed 60 characters")
    @Schema(description = "User last name.", example = "Perez Garcia")
    private String lastName;

    @NotNull(message = "height must not be null")
    @Min(value = 80, message = "height must be at least 80 cm")
    @Max(value = 260, message = "height must be at most 260 cm")
    @Schema(description = "Height in centimeters.", example = "180")
    private Integer height;

    @NotNull(message = "weight must not be null")
    @Min(value = 30, message = "weight must be at least 30 kg")
    @Max(value = 300, message = "weight must be at most 300 kg")
    @Schema(description = "Weight in kilograms.", example = "78")
    private Integer weight;

    @Past(message = "birthDate must be in the past")
    @Schema(description = "Birth date (ISO-8601).", example = "1992-05-10")
    private LocalDate birthDate;
}

