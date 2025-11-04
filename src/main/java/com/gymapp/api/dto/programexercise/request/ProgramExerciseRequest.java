package com.gymapp.api.dto.programexercise.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
    @Schema(description = "Number of sets.", example = "4")
    String sets;

    @NotNull(message = "reps must not be null")
    @Schema(description = "Number of repetitions per set.", example = "10")
    String reps;

    @Schema(description = "Rest time between sets.", example = "90s")
    String restTime;

    @Schema(description = "Day of the week for this exercise.", example = "Monday")
    String day;

    @Schema(description = "Additional notes for this exercise.", example = "Maintain controlled tempo")
    String notes;

    @Schema(description = "Order position within the day (1 = top).", example = "2")
    private Integer position;

}
