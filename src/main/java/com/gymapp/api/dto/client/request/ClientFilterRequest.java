package com.gymapp.api.dto.client.request;


import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientFilterRequest {

    @Parameter(description = "Filter by first name (partial, case-insensitive).")
    @Size(max = 50)
    private String firstName;

    @Parameter(description = "Filter by last name (partial, case-insensitive).")
    @Size(max = 50)
    private String lastName;
}
