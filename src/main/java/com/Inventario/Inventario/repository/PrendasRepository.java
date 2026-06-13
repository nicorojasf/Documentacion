package com.Inventario.Inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Inventario.Inventario.model.Prendas;

public interface PrendasRepository extends JpaRepository<Prendas, Integer> {

    Optional<Prendas> findById(Integer id);



}
