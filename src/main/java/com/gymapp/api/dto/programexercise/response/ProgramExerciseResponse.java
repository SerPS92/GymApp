package com.gymapp.api.dto.programexercise.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Exercise included in a training program.")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramExerciseResponse {

    @Schema(description = "Program exercise identifier.", example = "10")
    Long id;

    @Schema(description = "ID of the exercise associated with this program entry.", example = "1")
    Long exerciseId;

    @Schema(description = "Number of sets.", example = "4")
    String sets;

    @Schema(description = "Number of repetitions per set.", example = "10")
    String reps;

    @Schema(description = "Rest time between sets.", example = "90s")
    String restTime;

    @Schema(description = "Day of the week for this exercise.", example = "Day 1")
    String day;

    @Schema(description = "Additional notes for this exercise.", example = "Maintain controlled tempo")
    String notes;

    @Schema(description = "Training load or intensity reference.", example = "75% 1RM")
    String intensity;

    @Schema(description = "Movement tempo for the exercise.", example = "3-1-2-0")
    String tempo;

    @Schema(description = "Order position within the day (1 = top).", example = "2")
    Integer position;
}
