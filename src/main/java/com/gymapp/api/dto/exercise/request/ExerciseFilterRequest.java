package com.gymapp.api.dto.exercise.request;

import com.gymapp.domain.enums.Difficulty;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseFilterRequest {

    @Parameter(description = "Filter by name (partial, case-insensitive).")
    @Size(max = 50)
    private String name;

    @Parameter(description = "Filter by one or more category IDs (comma-separated). Example: categoryIds=1,3,5")
    private List<Long> categoryIds;

    @Parameter(description = "Filter by difficulty level.")
    private Difficulty difficulty;
}

