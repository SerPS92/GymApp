//package com.gymapp.api.controller.user;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.http.ProblemDetail;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/api/usuarios")
//@Tag(name = "Usuarios")
//public interface UserApi {
//
//    @Operation(summary = "Crear un nuevo usuario")
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "Usuario creado",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UsuarioResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Petición inválida",
//                    content = @Content(mediaType = "application/problem+json",
//                            schema = @Schema(implementation = ProblemDetail.class)))
//    })
//    @PostMapping
//    ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody CreateUsuarioRequest request);
//
//    @Operation(summary = "Obtener un usuario por ID")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UsuarioResponse.class))),
//            @ApiResponse(responseCode = "404", description = "No encontrado",
//                    content = @Content(mediaType = "application/problem+json",
//                            schema = @Schema(implementation = ProblemDetail.class)))
//    })
//    @GetMapping("/{usuarioId}")
//    ResponseEntity<UsuarioResponse> getUsuarioById(
//            @Parameter(description = "ID del usuario", required = true)
//            @PathVariable Long usuarioId);
//
//    @Operation(summary = "Actualizar un usuario existente")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Usuario actualizado",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UsuarioResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Petición inválida",
//                    content = @Content(mediaType = "application/problem+json",
//                            schema = @Schema(implementation = ProblemDetail.class))),
//            @ApiResponse(responseCode = "404", description = "No encontrado",
//                    content = @Content(mediaType = "application/problem+json",
//                            schema = @Schema(implementation = ProblemDetail.class)))
//    })
//    @PutMapping("/{usuarioId}")
//    ResponseEntity<UsuarioResponse> updateUsuario(
//            @PathVariable Long usuarioId,
//            @Valid @RequestBody UpdateUsuarioRequest request);
//
//    @Operation(summary = "Borrar un usuario")
//    @ApiResponses({
//            @ApiResponse(responseCode = "204", description = "Usuario borrado"),
//            @ApiResponse(responseCode = "404", description = "No encontrado",
//                    content = @Content(mediaType = "application/problem+json",
//                            schema = @Schema(implementation = ProblemDetail.class)))
//    })
//    @DeleteMapping("/{usuarioId}")
//    ResponseEntity<Void> deleteUsuario(@PathVariable Long usuarioId);
//}
//
