package com.gymapp.to.mapper;

import com.gymapp.model.Programa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramaMapper {
    Programa toTo(com.gymapp.to.domain.Programa programa);
    com.gymapp.to.domain.Programa toDomain(Programa programa);
}
