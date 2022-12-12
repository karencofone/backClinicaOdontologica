package com.example.IntegradorBackend.repository;

import com.example.IntegradorBackend.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("select o FROM Paciente o where o.dni= ?1")
    public Paciente findByDni(int dni);

    @Modifying
    @Transactional
    @Query("DELETE from Paciente")
    void deleteAll();

    @Modifying
    @Transactional
    @Query("update Paciente p set p.fechaAlta=?6, p.domicilio=?5, p.dni=?4 ,p.apellido=?3 ,p.nombre=?2 where p.id= ?1")
    void updateAll(Paciente paciente, Long idPaciente);
}
