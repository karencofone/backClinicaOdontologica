package com.example.IntegradorBackend.controllers;

import com.example.IntegradorBackend.entidades.Odontologo;
import com.example.IntegradorBackend.exception.RequestException;
import com.example.IntegradorBackend.service.OdontologoService;
import com.example.IntegradorBackend.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/odontologos")
public class OdontologoController {

    OdontologoService odontologoService;
    TurnoService turnoService;

    @PostMapping("/agregar")
        public ResponseEntity<?> agregar(@RequestBody Odontologo odontologo) {

            var nombre = odontologo.getNombre();
            var apellido = odontologo.getApellido();
            var matricula = odontologo.getMatricula();
            var getMatricula = odontologoService.buscarMatricula(matricula);

            if (getMatricula!= null){
                throw new RequestException("400 Bad Request","La matrícula ya existe");
            }
            if (nombre != null & apellido != null & matricula != null & !nombre.equals("") & !apellido.equals("") & !matricula.equals("")){
                odontologoService.agregar(odontologo);
                return new ResponseEntity<>("El odontólogo fue guardado con éxito", null, HttpStatus.CREATED);
           }else{
                throw new RequestException("400 Bad Request","Sintáxis inválida");
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Odontologo odontologo, @PathVariable Long id) {

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();
        var getId = odontologoService.buscarPorId(id);
        //var getMatricula = odontologoService.buscarMatricula(matricula);

        if (getId.isPresent()) {
            if (nombre != null & apellido != null & matricula != null & !nombre.equals("") & !apellido.equals("") & !matricula.equals("")){
                odontologoService.modificar(odontologo,id);
                return new ResponseEntity<>("El odontólogo fue actualizado con éxito", null, HttpStatus.CREATED);
            }else{
                throw new RequestException("400 Bad Request","Sintáxis inválida");
            }
        } else {
            throw new RequestException("400 Bad Request","No existe odontólogo para el id "+id);
        }
    }

    @GetMapping("/listar")
    public List<Odontologo> listar() {
        return odontologoService.listar();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        var getId = odontologoService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","No existe odontólogo para el id "+id);
        } if (!turnoService.buscarOdontologo(id).isEmpty() ){
            throw new RequestException("400 Bad Request",("No es posible eliminar al odontólogo id " + id + ", ya que tiene un turno asignado"));
        } else {
            odontologoService.eliminar(id);
            return new ResponseEntity<>(("El odontólogo con id "+id+" fue eliminado con exito"), null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        ResponseEntity<?> respuestaHttp = null;
        var getId = odontologoService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","Id {"+ id + "} no corresponde a ningún odontólogo");
        }
        else {
            respuestaHttp = ResponseEntity.ok(getId);
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<?> buscarMatricula(@PathVariable String matricula){

        ResponseEntity<?> respuestaHttp = null;
        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (getMatricula == null){
            throw new RequestException("400 Bad Request","No existe ningún odontólogo cuya matricula sea "+ matricula );
        } else {
            respuestaHttp = ResponseEntity.ok(getMatricula);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        odontologoService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente los registros de odontólogo";
        ResponseEntity<?> respuestaHttp = ResponseEntity.ok(respuesta);;
        return respuestaHttp;
    }
}