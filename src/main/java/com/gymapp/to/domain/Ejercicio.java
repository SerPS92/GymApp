package com.gymapp.to.domain;

import lombok.Data;

@Data
public class Ejercicio {
    private Integer id;
    private String nombre;
    private String grupoMuscular;
    private String imagen;
    private String descripcion;
}

