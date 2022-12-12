package com.example.IntegradorBackend.controllers;

import com.example.IntegradorBackend.entidades.Rol;
import com.example.IntegradorBackend.exception.RequestException;
import com.example.IntegradorBackend.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/roles")
    public class RolController {
        RolService rolService;

        @PostMapping("/agregar")
        public ResponseEntity<?> agregar(@RequestBody Rol rol) {
            var nombre = rol.getNombre();
            var buscarPorNombre = rolService.buscarPorNombre(nombre);

            if (buscarPorNombre!= null){
                throw new RequestException("400 Bad Request","Rol ya existente");
            }
            if (nombre != null &  !nombre.equals("")  ){
                rolService.agregar(rol);
                return new ResponseEntity<>("El rol fue guardado con éxito", null, HttpStatus.CREATED);
            } else{
                throw new RequestException("400 Bad Request","Sintáxis inválida");
            }
        }

        @GetMapping("/listar")
        public List<Rol> listar(){
            return rolService.listar();
        }

        @PutMapping("/modificar/{id}")
        public ResponseEntity<?> modificar(@RequestBody Rol rol, @PathVariable Long id) {

            var nombre = rol.getNombre();
            var buscarPorNombre = rolService.buscarPorNombre(nombre);
            var getId = rolService.buscarPorId(id);


            if (getId.isPresent()) {
                if (buscarPorNombre!= null){
                    throw new RequestException("400 Bad Request","Rol ya existente");
                }
                if (nombre != null &  !nombre.equals("")  ){
                    rolService.modificar(rol, id);
                    return new ResponseEntity<>("El rol fue actualizado con éxito", null, HttpStatus.CREATED);
                } else{
                    throw new RequestException("400 Bad Request","Sintáxis inválida");
                }
            } else {
                throw new RequestException("400 Bad Request","No existe rol para el id "+id);
            }
        }

        @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<?> eliminar(@PathVariable Long id){

            var getId = rolService.buscarPorId(id);

            if (getId.isEmpty()) {
                throw new RequestException("400 Bad Request","No existe rol para el id: "+id);
            } else {
                rolService.eliminar(id);
                return new ResponseEntity<>(("El rol con Id: "+id+" fue eliminado con éxito"), null, HttpStatus.CREATED);
            }
        }

        @GetMapping("/buscar/{id}")
        public ResponseEntity<?> buscarPorId(@PathVariable Long id){

            ResponseEntity<?> respuestaHttp = null;
            var getId = rolService.buscarPorId(id);

            if (getId.isEmpty()) {
                throw new RequestException("400 Bad Request","Id: {"+ id + "} no corresponde a ningún rol");
            }
            else {
                respuestaHttp = ResponseEntity.ok(getId);
            }
            return respuestaHttp;
        }
}
