package com.gymapp.controller;


import com.gymapp.api.EjerciciosApi;
import com.gymapp.model.Ejercicio;
import com.gymapp.to.mapper.EjercicioMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class EjerciciosController implements EjerciciosApi {

    private final EjercicioMapper ejercicioMapper;

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
        com.gymapp.to.domain.Ejercicio ejercicio = new com.gymapp.to.domain.Ejercicio();
        ejercicio.setId(1);
        ejercicio.setDescripcion("JAAJA");
        ejercicio.setNombre("Press");
        List<Ejercicio> ejercicios = new ArrayList<>();
        ejercicios.add(ejercicioMapper.toTO(ejercicio));
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> ejerciciosPost(Ejercicio ejercicio) {
        return null;
    }
}
