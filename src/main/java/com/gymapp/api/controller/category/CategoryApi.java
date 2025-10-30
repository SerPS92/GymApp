package com.gymapp.api.controller.category;

import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.request.CategoryFilterRequest;
import com.gymapp.api.dto.category.request.CategoryUpdateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/categories")
@Tag(name = "Categories")
public interface CategoryApi {

    @Operation(summary = "Get categories (paginated & filtered)")
    @ApiResponse(
            responseCode = "200",
            description = "Categories page",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PageResponseDTO.class))
    )
    @GetMapping
    ResponseEntity<PageResponseDTO<CategoryResponse>> getCategories(
            @Valid @ModelAttribute CategoryFilterRequest filter,
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) throws BadRequestException;


    @Operation(summary = "Create a new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created",
                    headers = @Header(name = "Location", description = "URI of the created category"),
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Category already exists",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest request);


    @Operation(summary = "Get category by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long categoryId);


    @Operation(summary = "Update an existing category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict (duplicate name)",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{categoryId}")
    ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequest request);


    @Operation(summary = "Delete a category")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId);
}

