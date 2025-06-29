package com.gymapp.to.mapper;

import com.gymapp.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toTo(com.gymapp.to.domain.Usuario usuario);
    com.gymapp.to.domain.Usuario toDomain(Usuario usuario);
}
