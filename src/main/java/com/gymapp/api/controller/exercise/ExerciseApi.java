package com.gymapp.api.controller.exercise;

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

@RequestMapping("/api/ejercicios")
@Tag(name = "Ejercicios")
public interface ExerciseApi {

    @Operation(summary = "Obtener todos los ejercicios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ejercicios",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EjercicioResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<EjercicioResponse>> getEjercicios();

    @Operation(summary = "Crear un nuevo ejercicio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ejercicio creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EjercicioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Petición inválida",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    ResponseEntity<EjercicioResponse> createEjercicio(@Valid @RequestBody CreateEjercicioRequest request);

    @Operation(summary = "Obtener un ejercicio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ejercicio encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EjercicioResponse.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{ejercicioId}")
    ResponseEntity<EjercicioResponse> getEjercicioById(
            @Parameter(description = "ID del ejercicio", required = true)
            @PathVariable Long ejercicioId);

    @Operation(summary = "Actualizar un ejercicio existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ejercicio actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EjercicioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Petición inválida",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{ejercicioId}")
    ResponseEntity<EjercicioResponse> updateEjercicio(
            @PathVariable Long ejercicioId,
            @Valid @RequestBody UpdateEjercicioRequest request);

    @Operation(summary = "Borrar un ejercicio")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ejercicio borrado"),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{ejercicioId}")
    ResponseEntity<Void> deleteEjercicio(@PathVariable Long ejercicioId);
}

