package com.aps.projetocarros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aps.projetocarros.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}