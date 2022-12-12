package com.example.IntegradorBackend.repository;
import com.example.IntegradorBackend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u FROM Usuario u where u.userName= ?1")
    public Usuario findByUserName(String username);
}
