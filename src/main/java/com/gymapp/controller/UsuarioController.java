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
    public ResponseEntity<Usuario> usuariosUsuarioIdGet(Integer usuarioId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Programa>> usuariosUsuarioIdProgramaGet(Integer usuarioId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> usuariosUsuarioIdPut(Integer usuarioId, Usuario usuario) {
        return null;
    }
}
