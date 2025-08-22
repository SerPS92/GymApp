package com.gymapp.application.mapper.exercise;

import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.domain.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseResponse toResponse(Exercise exercise);
}
