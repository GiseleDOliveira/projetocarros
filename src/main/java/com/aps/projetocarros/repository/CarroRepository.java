package com.aps.projetocarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aps.projetocarros.model.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long> {
}
