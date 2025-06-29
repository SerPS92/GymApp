package com.gymapp.to.domain;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class Programa {
    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Usuario usuario;
    private List<ProgramaEjercicio> ejercicios;
}
