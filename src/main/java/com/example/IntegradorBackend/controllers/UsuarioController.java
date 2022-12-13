package com.example.IntegradorBackend.controllers;

import com.example.IntegradorBackend.dto.RolUsuarioDTO;
import com.example.IntegradorBackend.entidades.Usuario;
import com.example.IntegradorBackend.exception.RequestException;
import com.example.IntegradorBackend.service.RolService;
import com.example.IntegradorBackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private RolService rolService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Usuario usuario) {
        var email = usuario.getUserName();
        var password = usuario.getPassword();

        if (/*email != null &*/ password != null & !password.equals("") /*& !email.equals("")*/){
            usuarioService.agregar(usuario);
            return new ResponseEntity<>("El usuario fue guardado con éxito", null, HttpStatus.CREATED);
        } else{
            throw new RequestException("400 Bad Request","Sintaxis inválida");
        }
    }

    @PostMapping("/agregarRol")
    public ResponseEntity<?> agregarRol(@RequestBody RolUsuarioDTO formRol) {
            var email = formRol.getUserName();
            var nameRol = formRol.getNombreRol();
                usuarioService.agregarRol(formRol);
                return new ResponseEntity<>("El rol fue agregado con éxito", null, HttpStatus.CREATED);
            //}
    }

    @GetMapping("/listar")
        public List<Usuario> listar(){
            return usuarioService.listarUsuario();
    }

    @PutMapping("/modificar/{id}")
        public ResponseEntity<?> modificar(@RequestBody Usuario usuario, @PathVariable Long id) {

            var email = usuario.getUserName();
            var password = usuario.getPassword();

            var getId = usuarioService.buscarPorId(id);
            //var buscarPorEmail = usuarioService.buscarPorEmail(email);

            if (getId.isPresent()) {
                //if (buscarPorEmail!= null){
                  //  throw new RequestException("400 Bad Request","El email ya existe");
                //}
                if (/*email != null &*/ password != null /*& !email.equals("")*/ & !password.equals("") ){
                    usuarioService.modificar(usuario, id);
                    return new ResponseEntity<>("El usuario fue actualizado con éxito", null, HttpStatus.CREATED);
                } else{
                    throw new RequestException("400 Bad Request","Sintaxis inválida");
                }
            } else {
                throw new RequestException("400 Bad Request","No existe usuario para el id "+id);
            }
    }

    @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<?> eliminar(@PathVariable Long id){

            var getId = usuarioService.buscarPorId(id);

            if (getId.isEmpty()) {
                throw new RequestException("400 Bad Request","No existe usuario para el id "+id);
            } else {
                usuarioService.eliminar(id);
                return new ResponseEntity<>(("El usuario con id "+id+" fue eliminado con éxito"), null, HttpStatus.CREATED);
            }
    }

    @GetMapping("/buscar/{id}")
        public ResponseEntity<?> buscarPorId(@PathVariable Long id){

            ResponseEntity<?> respuestaHttp = null;
            var getId = usuarioService.buscarPorId(id);

            if (getId.isEmpty()) {
                throw new RequestException("400 Bad Request","Id {"+ id + "} no corresponde a ningún usuario");
            } else {
                respuestaHttp = ResponseEntity.ok(getId);
            }
            return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
        public ResponseEntity<?> eliminarTodos(){
            usuarioService.borrarTodos();
            String respuesta ="\n"+"Se eliminaron correctamente todos los registros de usuario";
            ResponseEntity<?> respuestaHttp = ResponseEntity.ok(respuesta);;
            return respuestaHttp;
    }
}
