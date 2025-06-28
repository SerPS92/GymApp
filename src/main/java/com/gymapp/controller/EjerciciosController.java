package com.gymapp.controller;


import com.gymapp.api.EjerciciosApi;
import com.gymapp.model.Ejercicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EjerciciosController implements EjerciciosApi {

    @Override
    public ResponseEntity<Ejercicio> ejerciciosEjercicioIdGet(Integer ejercicioId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> ejerciciosEjercicioIdPut(Integer ejercicioId, Ejercicio ejercicio) {
        return null;
    }

    @Override
    public ResponseEntity<List<Ejercicio>> ejerciciosGet() {
        return null;
    }

    @Override
    public ResponseEntity<Void> ejerciciosPost(Ejercicio ejercicio) {
        return null;
    }
}
