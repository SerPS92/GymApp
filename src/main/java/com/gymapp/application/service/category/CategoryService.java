package com.gymapp.application.service.category;

import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.request.CategoryFilterRequest;
import com.gymapp.api.dto.category.request.CategoryUpdateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.exception.BadRequestException;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    PageResponseDTO<CategoryResponse> search(CategoryFilterRequest filterRequest, Pageable pageable) throws BadRequestException;
    CategoryResponse getById(Long id);
    CategoryResponse createCategory(CategoryCreateRequest request);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);
    void deleteCategory(Long id);
}
