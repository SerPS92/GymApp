package com.gymapp.application.mapper.exercise;

import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.domain.entity.Category;
import com.gymapp.domain.entity.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(target = "categories", expression = "java(mapCategoriesToResponses(exercise.getCategories()))")
    ExerciseResponse toResponse(Exercise exercise);

    @Mapping(target = "categories", expression = "java(mapCategoryIdsToEntities(request.getCategoryIds()))")
    Exercise toEntity(ExerciseCreateRequest request);

    default Set<Category> mapCategoryIdsToEntities(List<Long> ids) {
        if (ids == null) return Set.of();
        return ids.stream()
                .map(id -> Category.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default List<CategoryResponse> mapCategoriesToResponses(Set<Category> categories) {
        if (categories == null) return List.of();
        return categories.stream()
                .map(cat -> CategoryResponse.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .build())
                .toList();
    }

}
