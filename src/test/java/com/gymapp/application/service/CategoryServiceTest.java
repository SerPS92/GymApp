package com.gymapp.application.service;


import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.request.CategoryFilterRequest;
import com.gymapp.api.dto.category.request.CategoryUpdateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.application.mapper.category.CategoryMapper;
import com.gymapp.application.service.category.CategoryServiceImpl;
import com.gymapp.domain.entity.Category;
import com.gymapp.infrastructure.persistence.CategoryJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ConflictException;
import com.gymapp.shared.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryJpaRepository repository;

    @Mock
    private CategoryMapper mapper;

    @Test
    @DisplayName("Should return paginated list of mapped categories")
    void searchAll_ok() {
        Pageable pageable = PageRequest.of(0, 2);
        Category c1 = new Category(1L, "Chest");
        Category c2 = new Category(2L, "Legs");

        Page<Category> page = new PageImpl<>(List.of(c1, c2), pageable, 2);
        when(repository.findAll(pageable)).thenReturn(page);

        CategoryResponse r1 = CategoryResponse.builder().id(1L).name("Chest").build();
        CategoryResponse r2 = CategoryResponse.builder().id(2L).name("Legs").build();
        when(mapper.toResponse(c1)).thenReturn(r1);
        when(mapper.toResponse(c2)).thenReturn(r2);

        PageResponseDTO<CategoryResponse> result = categoryService.search(new CategoryFilterRequest(), pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getPage()).isZero();
        assertThat(result.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return category by id when exists")
    void getById_ok() {
        Category c = new Category(1L, "Chest");
        CategoryResponse r = CategoryResponse.builder().id(1L).name("Chest").build();

        when(repository.findById(1L)).thenReturn(Optional.of(c));
        when(mapper.toResponse(c)).thenReturn(r);

        CategoryResponse result = categoryService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Chest", result.getName());
    }

    @Test
    @DisplayName("Should throw AppException when category not found by id")
    void getById_notFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        AppException ex = assertThrows(AppException.class, () -> categoryService.getById(999L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.NOT_FOUND, ex.getCode());
        assertTrue(ex.getMessage().contains("999"));
    }

    @Test
    @DisplayName("Should create new category when name is unique")
    void createCategory_ok() {
        CategoryCreateRequest req = CategoryCreateRequest.builder().name("NewCat").build();
        Category entity = new Category(null, "NewCat");
        Category saved = new Category(10L, "NewCat");
        CategoryResponse resp = CategoryResponse.builder().id(10L).name("NewCat").build();

        when(repository.existsByNameIgnoreCase("NewCat")).thenReturn(false);
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(resp);

        CategoryResponse result = categoryService.createCategory(req);

        assertEquals(10L, result.getId());
        assertEquals("NewCat", result.getName());
    }

    @Test
    @DisplayName("Should throw ConflictException when creating duplicate category")
    void createCategory_conflict() {
        CategoryCreateRequest req = CategoryCreateRequest.builder().name("  Back  ").build();
        when(repository.existsByNameIgnoreCase("Back")).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class, () -> categoryService.createCategory(req));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.CONFLICT, ex.getCode());
    }

    @Test
    @DisplayName("Should update category name successfully")
    void updateCategory_ok() {
        Long id = 1L;
        Category existing = new Category(id, "OldName");
        CategoryUpdateRequest req = CategoryUpdateRequest.builder().name("Updated").build();
        Category saved = new Category(id, "Updated");
        CategoryResponse resp = CategoryResponse.builder().id(id).name("Updated").build();

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(resp);

        CategoryResponse result = categoryService.updateCategory(id, req);

        assertEquals(id, result.getId());
        assertEquals("Updated", result.getName());
    }

    @Test
    @DisplayName("Should throw 404 when updating non-existent category")
    void updateCategory_notFound() {
        Long id = 999L;
        CategoryUpdateRequest req = CategoryUpdateRequest.builder().name("Updated").build();
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> categoryService.updateCategory(id, req));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("Should delete category successfully when exists")
    void deleteCategory_ok() {
        when(repository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
    }

    @Test
    @DisplayName("Should throw 404 when deleting non-existent category")
    void deleteCategory_notFound() {
        when(repository.existsById(99L)).thenReturn(false);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> categoryService.deleteCategory(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("Should throw ConflictException when delete fails due to DataIntegrityViolation")
    void deleteCategory_conflict() {
        when(repository.existsById(1L)).thenReturn(true);
        doThrow(new DataIntegrityViolationException("ref constraint")).when(repository).deleteById(1L);

        ConflictException ex = assertThrows(ConflictException.class, () -> categoryService.deleteCategory(1L));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.CONFLICT, ex.getCode());
    }
}
