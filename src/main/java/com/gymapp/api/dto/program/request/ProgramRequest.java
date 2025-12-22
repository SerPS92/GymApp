package com.gymapp.api.dto.program.request;

import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.enums.PdfFormatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data to generate or create a program.")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramRequest {

    @Schema(description = "Start date of the program.", example = "2025-11-01")
    LocalDate startDate;

    @Schema(description = "End date of the program.", example = "2025-12-01")
    LocalDate endDate;

    @Schema(description = "Custom title of the training program.", example = "Upper/Lower Routine 3 Days")
    @Size(max = 35, message = "title must not exceed 35 characters")
    String title;

    @Schema(description = "Additional notes or recommendations displayed at the end of the PDF. " +
            "Each item represents a separate note line.", example = "Increase weight progressively")
    @Size(max = 15, message = "notes cannot contain more than 15 items")
    List<@Size(max = 80, message = "each note must not exceed 80 characters") String> notes;

    @Schema(description = "Custom labels for each day (e.g. Day 1 → Push, Day 2 → Pull)",
            example = "{\"Day 1\": \"Push\", \"Day 2\": \"Pull\"}")
    @Size(max = 30)
    Map<String, String> dayLabels;


    @Schema(description = "Desired PDF layout format.", example = "CALENDAR")
    PdfFormatType pdfFormatType;

    @Valid
    @Schema(description = "List of exercises included in the program.")
    List<ProgramExerciseRequest> programExercises;
}
