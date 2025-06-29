package com.gymapp.to.domain;

import lombok.Data;

@Data
public class ProgramaEjercicio {
    private Integer id;
    private Ejercicio ejercicio;
    private Integer repeticiones;
    private Integer series;
    private Integer descansos;
    private String dia;
    private String notas;
    private Integer programaId;
}
