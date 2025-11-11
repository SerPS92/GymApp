package com.gymapp.application.pdf.mapper;

import com.gymapp.application.pdf.dto.PdfExerciseDto;
import com.gymapp.domain.entity.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PdfExerciseMapper {
    PdfExerciseMapper INSTANCE = Mappers.getMapper(PdfExerciseMapper.class);

    PdfExerciseDto toDto(Exercise exercise);
}
