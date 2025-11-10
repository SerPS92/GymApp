package com.gymapp.api.controller.category;

import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.request.CategoryFilterRequest;
import com.gymapp.api.dto.category.request.CategoryUpdateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.application.service.category.CategoryService;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoryApi{

    private final CategoryService service;

    @Override
    public ResponseEntity<PageResponseDTO<CategoryResponse>> getCategories(
            @Valid CategoryFilterRequest filter, Pageable pageable) throws BadRequestException {
        PageResponseDTO<CategoryResponse> responseDTO = service.search(filter, pageable);
        log.info("Content size: {}", responseDTO.getContent().size());
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<CategoryResponse> createCategory(@Valid CategoryCreateRequest request) {
        CategoryResponse created = service.createCategory(request);

        java.net.URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        log.info("Category created: {}", created);
        return ResponseEntity.created(location).body(created);
    }

    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(Long categoryId) {
        CategoryResponse response = service.getById(categoryId);
        log.info("Category found: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(Long categoryId,@Valid CategoryUpdateRequest request) {
        CategoryResponse updated = service.updateCategory(categoryId, request);
        log.info("Category with Id: {} is updated", categoryId);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long categoryId) {
        service.deleteCategory(categoryId);
        log.info("Category deleted with ID: {}", categoryId);
        return ResponseEntity.noContent().build();
    }
}
