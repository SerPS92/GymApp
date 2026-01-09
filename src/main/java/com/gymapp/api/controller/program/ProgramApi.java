package com.gymapp.api.controller.program;

import com.gymapp.api.dto.program.request.ProgramFilterRequest;
import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.program.response.ProgramResponse;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.exception.BadRequestException;
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

@RequestMapping("/api/programs")
@Tag(name = "Programs")
public interface ProgramApi {

    @Operation(summary = "Get programs (paginated & filtered)")
    @ApiResponse(
            responseCode = "200",
            description = "Programs page",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PageResponseDTO.class))
    )
    @GetMapping
    ResponseEntity<PageResponseDTO<ProgramResponse>> getPrograms(
            @Valid @ModelAttribute ProgramFilterRequest filter,
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) throws BadRequestException;

    @Operation(summary = "Get program by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{programId}")
    ResponseEntity<ProgramResponse> getProgramById(
            @Parameter(description = "Program ID", required = true)
            @PathVariable Long programId
    );

    @Operation(summary = "Create a new program")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Program created",
                    headers = @Header(name = "Location", description = "URI of the created program"),
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    ResponseEntity<ProgramResponse> createProgram(
            @Valid @RequestBody ProgramRequest request
    );

    @Operation(summary = "Update an existing program")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{programId}")
    ResponseEntity<ProgramResponse> updateProgram(
            @Parameter(description = "Program ID", required = true)
            @PathVariable Long programId,
            @Valid @RequestBody ProgramRequest request
    );

    @Operation(summary = "Delete a program")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Program deleted"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{programId}")
    ResponseEntity<Void> deleteProgram(
            @PathVariable Long programId
    );

    @Operation(summary = "Generate PDF for a training program")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PDF generated successfully",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/pdf")
    ResponseEntity<byte[]> generateProgramPdf(
            @Valid @RequestBody ProgramRequest request
    );
}

