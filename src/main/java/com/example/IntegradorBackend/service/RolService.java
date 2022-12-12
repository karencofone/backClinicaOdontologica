package com.example.IntegradorBackend.service;
import com.example.IntegradorBackend.entidades.Rol;
import com.example.IntegradorBackend.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RolService {
    private final RolRepository rolRepository;
    public List<com.example.IntegradorBackend.entidades.Rol> listar() {
        log.info("Listando Roles");
        return rolRepository.findAll();
    }
    public Rol agregar(Rol rol) {
        log.info("Guardar nuevo Rol en la base de datos: "+rol.getNombre());
        return rolRepository.save(rol);
    }
    public Rol buscarPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }
    public void modificar(Rol rol, Long id) {
        var rolNew = rolRepository.findById(id).get();
        if(rol.getNombre() != null & !rol.getNombre().equals("")) rolNew.setNombre(rol.getNombre());
        rolRepository.save(rolNew);
    }
    public void eliminar(Long  id) {
        rolRepository.deleteById(id);
    }
}
