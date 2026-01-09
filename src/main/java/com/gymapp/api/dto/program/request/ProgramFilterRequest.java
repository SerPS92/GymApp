package com.gymapp.api.dto.program.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramFilterRequest {

    @Parameter(description = "Filter by program title (partial, case-insensitive).")
    @Size(max = 35)
    String title;

    @Parameter(description = "Filter by client name (partial, case-insensitive).")
    @Size(max = 60)
    String clientName;

}
