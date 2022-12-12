package com.example.IntegradorBackend.controllers;

import com.example.IntegradorBackend.entidades.Paciente;
import com.example.IntegradorBackend.exception.RequestException;
import com.example.IntegradorBackend.service.PacienteService;
import com.example.IntegradorBackend.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/pacientes")
public class PacienteController {
    PacienteService pacienteService;
    TurnoService turnoService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Paciente paciente){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = paciente.getNombre();
        var apellido = paciente.getApellido();
        var dni = paciente.getDni();
        var domicilio = paciente.getDomicilio();
        var fechaAlta = paciente.getFechaAlta();
        var getDni = pacienteService.buscarDNI(dni);

        if (getDni!= null){
            throw new RequestException("400 Bad Request","DNI ya existente");
        }
        if (nombre != null & !nombre.equals("") & apellido != null & !apellido.equals("") & domicilio != null & !domicilio.equals("") & dni != 0  & fechaAlta !=null ){
            pacienteService.agregar(paciente);
            return new ResponseEntity<>("El paciente fue guardado con exito", null, HttpStatus.CREATED);
        } else{
            throw new RequestException("400 Bad Request","Sintaxis inválida");
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Paciente paciente, @PathVariable Long id){
        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = paciente.getNombre();
        var apellido = paciente.getApellido();
        var domicilio = paciente.getDomicilio();
        var dni = paciente.getDni();
        var fechaAlta = paciente.getFechaAlta();
        var getPacienteId = pacienteService.buscarPorId(id);

        if (getPacienteId.isPresent()) {
            if (nombre != null & !nombre.equals("") & apellido != null & !apellido.equals("") & domicilio != null & !domicilio.equals("") & dni != 0 & fechaAlta !=null ){
                pacienteService.modificar(paciente,id);
                return new ResponseEntity<>("El paciente fue actualizado con exito", null, HttpStatus.CREATED);
            } else{
                throw new RequestException("400 Bad Request","Sintaxis inválida");
            }
        } else {
            throw new RequestException("400 Bad Request","No existe ningún paciente para el id "+id);
        }
    }

    @GetMapping("/listar")
    public List<Paciente> listar(){
        return pacienteService.listar();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        var getId = pacienteService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","No existe paciente para el id "+id);
        } if (!turnoService.buscarPaciente(id).isEmpty() ){
            throw new RequestException("400 Bad Request",("No es posible eliminar al paciente id " + id + ", ya que tiene un turno asignado"));
        } else {
            pacienteService.eliminar(id);
            return new ResponseEntity<>(("El paciente con id "+id+" fue eliminado con éxito"), null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        ResponseEntity<?> respuestaHttp = null;
        var getId = pacienteService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","Id: {"+ id + "} no corresponde a ningún paciente");
        } else {
            respuestaHttp = ResponseEntity.ok(getId);
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarDni/{dni}")
    public ResponseEntity<?> buscarDni(@PathVariable int dni){

        ResponseEntity<?> respuestaHttp = null;
        var getDni = pacienteService.buscarDNI(dni);

        if (getDni == null){
            throw new RequestException("400 Bad Request","No existe ningún paciente con DNI "+ dni );
        } else {
            respuestaHttp = ResponseEntity.ok(getDni);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        pacienteService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente todos los registros de paciente";
        return ResponseEntity.ok(respuesta);
    }


}
