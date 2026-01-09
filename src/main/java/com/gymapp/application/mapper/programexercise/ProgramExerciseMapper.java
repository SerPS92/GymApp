package com.gymapp.application.mapper.programexercise;

import com.gymapp.api.dto.programexercise.response.ProgramExerciseResponse;
import com.gymapp.domain.entity.ProgramExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProgramExerciseMapper {

    @Mapping(source = "exercise.id", target = "exerciseId")
    ProgramExerciseResponse toResponse(ProgramExercise programExercise);
}
