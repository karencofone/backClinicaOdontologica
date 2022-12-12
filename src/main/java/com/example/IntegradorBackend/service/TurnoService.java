package com.example.IntegradorBackend.service;

import com.example.IntegradorBackend.entidades.Odontologo;
import com.example.IntegradorBackend.entidades.Turno;
import com.example.IntegradorBackend.dto.TurnoDTO;
import com.example.IntegradorBackend.repository.TurnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    public List<Turno> listar() {
        return turnoRepository.findAll();
    }
    public void modificar(Turno turno, Long id) {

        var turnoNew = turnoRepository.findById(id).get();
        var fecha = turno.getFecha();

        var odontologo = odontologoService.buscarPorId(turno.getOdontologo());
        var paciente = pacienteService.buscarPorId(turno.getPaciente());

        odontologo.ifPresent(turnoNew::setOdontologo);
        paciente.ifPresent(turnoNew::setPaciente);
        if (fecha != null )turnoNew.setFecha(fecha);
        turnoRepository.save(turnoNew);
    }

    public void eliminar(Long  id) {
        turnoRepository.deleteById(id);
    }
    public Optional<Turno> buscarPorId(Long id) {
        return turnoRepository.findById(id);
    }
    public List<Turno> buscarOdontologo(Long id){
        return turnoRepository.findByOdontologo(id);
    }
    public List<Turno> buscarPaciente(Long id){
        return turnoRepository.findByPaciente(id);
    }
    public void deleteOdontologo(Long id){
        turnoRepository.deleteByOdontologo(id);
    }
    public void deletePaciente(Long id){
        turnoRepository.deleteByPaciente(id);
    }

    public void agregar(Turno t){
        turnoRepository.save(t);
    }

    //public Turno agregarDTO (TurnoDTO turnoDto){
      //  var odontologo = odontologoService.buscarPorId(turnoDto.getIdOdontologo()).get();
        //var paciente = pacienteService.buscarPorId(turnoDto.getIdPaciente()).get();
        //var fecha = turnoDto.getFecha();
        //var turnoNew = new Turno();
        //turnoNew.setOdontologo(odontologo);
        //turnoNew.setPaciente(paciente);
        //turnoNew.setFecha(fecha);
        //turnoRepository.save(turnoNew);
        //return turnoNew;
    //}

    public void borrarTodos(){
        turnoRepository.deleteAll();
    }



}
