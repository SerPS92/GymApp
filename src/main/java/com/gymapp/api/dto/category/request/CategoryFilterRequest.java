package com.gymapp.api.dto.category.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFilterRequest {

    @Parameter(description = "Filter by name (partial, case-insensitive).")
    @Size(max = 50)
    private String name;
}

