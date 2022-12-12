package com.example.IntegradorBackend.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TurnoDTO {

    private Long idPaciente;
    private Long idOdontologo;
    private LocalDateTime fecha;

}
