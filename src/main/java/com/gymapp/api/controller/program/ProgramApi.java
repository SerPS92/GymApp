package com.gymapp.api.controller.program;


import com.gymapp.contract.programa.dto.request.CreateProgramaRequest;
import com.gymapp.contract.programa.dto.response.ProgramaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Programas")
public interface ProgramApi {

    @Operation(summary = "Crear un nuevo programa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Programa creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProgramaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Petición inválida",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/api/programas")
    ResponseEntity<ProgramaResponse> createPrograma(@Valid @RequestBody CreateProgramaRequest request);

    @Operation(summary = "Obtener un programa por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Programa encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProgramaResponse.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/api/programas/{programaId}")
    ResponseEntity<ProgramaResponse> getProgramaById(
            @Parameter(description = "ID del programa", required = true)
            @PathVariable Long programaId);

    @Operation(summary = "Borrar un programa")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Programa borrado"),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/api/programas/{programaId}")
    ResponseEntity<Void> deletePrograma(@PathVariable Long programaId);

    @Operation(summary = "Obtener todos los programas de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de programas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProgramaResponse.class)))
    })
    @GetMapping("/api/usuarios/{usuarioId}/programa")
    ResponseEntity<List<ProgramaResponse>> getProgramasByUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long usuarioId);
}

