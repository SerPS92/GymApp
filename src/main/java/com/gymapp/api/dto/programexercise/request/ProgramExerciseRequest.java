package com.gymapp.api.dto.programexercise.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Exercise included in a training program.")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramExerciseRequest {

    @Schema(description = "ID of the exercise associated with this program entry.", example = "1")
    @NotNull(message = "exerciseId must not be null")
    private Long exerciseId;

    @NotNull(message = "sets must not be null")
    @Size(max = 2, message = "sets must not exceed 2 characters")
    @Schema(description = "Number of sets.", example = "4")
    String sets;

    @NotNull(message = "reps must not be null")
    @Size(max = 5, message = "reps must not exceed 5 characters")
    @Schema(description = "Number of repetitions per set.", example = "10")
    String reps;

    @Size(max = 3, message = "restTime must not exceed 3 characters")
    @Schema(description = "Rest time between sets.", example = "90s")
    String restTime;

    @Schema(description = "Day of the week for this exercise.", example = "Day 1, Day 2")
    @NotNull(message = "day cannot be null")
    String day;

    @Size(max = 15, message = "notes must not exceed 15 characters")
    @Schema(description = "Additional notes for this exercise.", example = "Maintain controlled tempo")
    String notes;

    @Size(max = 8, message = "notes must not exceed 8 characters")
    @Schema(description = "Training load or intensity reference for the exercise. " +
            "Can represent absolute or relative effort (e.g., weight, %1RM, RPE, or RM).", example = "75% 1RM")
    String intensity;

    @Size(max = 8, message = "tempo must not exceed 8 characters")
    @Schema(description = "Movement tempo for the exercise (e.g., 3-1-2-0).", example = "3-1-2-0")
    String tempo;

    @Schema(description = "Order position within the day (1 = top).", example = "2")
    @NotNull(message = "position must not be null")
    @Min(value = 1, message = "position must be at least 1")
    @Max(value = 20, message = "position must not exceed 20")
    Integer position;

}
