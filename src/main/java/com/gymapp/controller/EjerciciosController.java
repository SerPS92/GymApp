package com.gymapp.controller;


import com.gymapp.api.EjerciciosApi;
import com.gymapp.model.Ejercicio;
import com.gymapp.to.mapper.EjercicioMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EjerciciosController implements EjerciciosApi {

    private final EjercicioMapper ejercicioMapper;

    @Override
    public ResponseEntity<Ejercicio> ejerciciosEjercicioIdGet(Long ejercicioId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> ejerciciosEjercicioIdPut(Long ejercicioId, Ejercicio ejercicio) {
        return null;
    }

    @Override
    public ResponseEntity<List<Ejercicio>> ejerciciosGet() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> ejerciciosPost(Ejercicio ejercicio) {
        return null;
    }
}
