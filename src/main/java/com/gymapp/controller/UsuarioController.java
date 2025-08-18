package com.gymapp.controller;

import com.gymapp.api.UsuariosApi;
import com.gymapp.model.Programa;
import com.gymapp.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UsuarioController implements UsuariosApi {

    @Override
    public ResponseEntity<Void> usuariosPost(Usuario usuario) {
        return null;
    }

    @Override
    public ResponseEntity<Usuario> usuariosUsuarioIdGet(Long usuarioId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Programa>> usuariosUsuarioIdProgramaGet(Long usuarioId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> usuariosUsuarioIdPut(Long usuarioId, Usuario usuario) {
        return null;
    }
}
