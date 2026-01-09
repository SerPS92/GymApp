package com.gymapp.api.dto.program.response;


import com.gymapp.api.dto.programexercise.response.ProgramExerciseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Program data returned after creation or retrieval.")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramResponse {

    @Schema(description = "Program identifier.", example = "1")
    Long id;

    @Schema(description = "Start date of the program.", example = "2025-11-01")
    LocalDate startDate;

    @Schema(description = "End date of the program.", example = "2025-12-01")
    LocalDate endDate;

    @Schema(description = "Custom title of the training program.", example = "Upper/Lower Routine 3 Days")
    String title;

    @Schema(description = "Name of the client for whom the training program is created.", example = "John Doe")
    String clientName;

    @Schema(description = "Additional notes or recommendations.")
    List<String> notes;

    @Schema(description = "Custom labels for each day (e.g. Day 1 → Push, Day 2 → Pull)",
            example = "{\"Day 1\": \"Push\", \"Day 2\": \"Pull\"}")
    Map<String, String> dayLabels;

    @Schema(description = "List of exercises included in the program.")
    List<ProgramExerciseResponse> programExercises;
}

