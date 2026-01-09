package com.gymapp.application.mapper.program;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.program.response.ProgramResponse;
import com.gymapp.application.mapper.programexercise.ProgramExerciseMapper;
import com.gymapp.domain.entity.Program;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = ProgramExerciseMapper.class)
public interface ProgramMapper {

    ProgramResponse toResponse(Program program);

    @Mapping(target = "programExercises", ignore = true)
    Program toEntity(ProgramRequest programRequest);

}
