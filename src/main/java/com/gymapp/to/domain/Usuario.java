package com.gymapp.to.domain;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class Usuario {
    private Integer id;
    private String nombre;
    private String apellidos;
    private Integer altura;
    private Integer peso;
    private LocalDate fechaNacimiento;
    private List<Programa> programas;
}

