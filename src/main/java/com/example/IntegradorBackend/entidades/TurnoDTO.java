package com.example.IntegradorBackend.entidades;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoDTO {

    private Long id_paciente;
    private Long id_odontologo;
    private LocalDate fecha;

}
