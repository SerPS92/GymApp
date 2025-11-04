package com.gymapp.api.dto.program.request;

import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.enums.PdfFormatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data to generate or create a program.")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramRequest {

    @NotNull(message = "startDate must not be null")
    @Schema(description = "Start date of the program.", example = "2025-11-01")
    LocalDate startDate;

    @NotNull(message = "endDate must not be null")
    @Schema(description = "End date of the program.", example = "2025-12-01")
    LocalDate endDate;

    @Schema(description = "Custom title of the training program.", example = "Upper/Lower Routine 3 Days")
    private String title;

    @Schema(description = "Desired PDF layout format.", example = "CALENDAR")
    private PdfFormatType pdfFormatType;

    @Valid
    @Schema(description = "List of exercises included in the program.")
    List<ProgramExerciseRequest> programExercises;
}
