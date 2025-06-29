package com.gymapp.to.mapper;

import com.gymapp.model.Ejercicio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EjercicioMapper {

    Ejercicio toTO(com.gymapp.to.domain.Ejercicio ejercicio);
    com.gymapp.to.domain.Ejercicio toDomain(Ejercicio ejercicio);
}
