package com.gymapp.application.mapper.category;

import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);
    Category toEntity(CategoryCreateRequest categoryCreateRequest);
}
