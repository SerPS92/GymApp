package com.gymapp.api.controller.ejercicio;


import com.gymapp.api.EjerciciosApi;
import com.gymapp.model.Ejercicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EjercicioController implements EjerciciosApi {


    @Override
    public ResponseEntity<Void> ejerciciosEjercicioIdDelete(Long ejercicioId) {
        return null;
    }

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
        return null;
    }

    @Override
    public ResponseEntity<Void> ejerciciosPost(Ejercicio ejercicio) {
        return null;
    }
}
