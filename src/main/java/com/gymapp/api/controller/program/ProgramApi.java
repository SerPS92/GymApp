package com.gymapp.api.controller.program;

import com.gymapp.api.dto.program.request.ProgramRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/programs")
@Tag(name = "Programs")
public interface ProgramApi {

    @Operation(summary = "Get all programs (future implementation)")
    @ApiResponse(responseCode = "200", description = "Programs list (not yet implemented)")
    @GetMapping
    ResponseEntity<Void> getPrograms();

    @Operation(summary = "Get program by ID (future implementation)")
    @ApiResponse(responseCode = "200", description = "Program details (not yet implemented)")
    @GetMapping("/{programId}")
    ResponseEntity<Void> getProgramById(@PathVariable Long programId);

    @Operation(summary = "Create a new program (future implementation)")
    @ApiResponse(responseCode = "201", description = "Program created (not yet implemented)")
    @PostMapping
    ResponseEntity<Void> createProgram(@RequestBody ProgramRequest request);

    @Operation(summary = "Update a program (future implementation)")
    @ApiResponse(responseCode = "200", description = "Program updated (not yet implemented)")
    @PutMapping("/{programId}")
    ResponseEntity<Void> updateProgram(@PathVariable Long programId, @RequestBody ProgramRequest request);

    @Operation(summary = "Delete a program (future implementation)")
    @ApiResponse(responseCode = "204", description = "Program deleted (not yet implemented)")
    @DeleteMapping("/{programId}")
    ResponseEntity<Void> deleteProgram(@PathVariable Long programId);

    @Operation(summary = "Generate PDF for a training program")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PDF generated successfully",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = org.springframework.http.ProblemDetail.class)))
    })
    @PostMapping("/pdf")
    ResponseEntity<byte[]> generateProgramPdf(@Valid @RequestBody ProgramRequest request);
}
