package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos herdados de JpaRepository (declarados explicitamente para fins educacionais)
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
}
