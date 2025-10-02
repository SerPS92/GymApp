package com.gymapp.api.controller.exercise;

import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/exercises")
@Tag(name = "Exercises")
public interface ExerciseApi {

    @Operation(summary = "Get exercises (paginated & filtered)")

    @ApiResponse(responseCode = "200", description = "Exercises page",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponseDTO.class)))

    @GetMapping
    ResponseEntity<PageResponseDTO<ExerciseResponse>> getExercises(
            @Valid @ModelAttribute ExerciseFilterRequest filter,
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) throws BadRequestException;

    @Operation(summary = "Create a new exercise")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Exercise created",
                    headers = @Header(name = "Location", description = "URI of the created exercise"),
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExerciseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Exercise already exists",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PostMapping
    ResponseEntity<ExerciseResponse> createExercise(@Valid @RequestBody ExerciseCreateRequest request);

    @Operation(summary = "Get exercise by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exercise found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExerciseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{exerciseId}")
    ResponseEntity<ExerciseResponse> getExerciseById(
            @Parameter(description = "Exercise ID", required = true)
            @PathVariable Long exerciseId);

    @Operation(summary = "Partially update an existing exercise (PATCH)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exercise updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExerciseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict (duplicate exercise)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping(
            value = "/{exerciseId}",
            consumes = "application/json",
            produces = "application/json"
    )
    ResponseEntity<ExerciseResponse> updateExercise(
            @Parameter(description = "Exercise ID", required = true)
            @PathVariable Long exerciseId,
            @Valid @RequestBody ExerciseUpdateRequest request);


    @Operation(summary = "Delete an exercise")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exercise deleted"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{exerciseId}")
    ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId);
}
