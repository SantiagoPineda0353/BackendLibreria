package com.example.BackendLibreria.repository;

import com.example.BackendLibreria.model.Ejemplar;
import com.example.BackendLibreria.model.EstadoEjemplar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EjemplarRepository extends JpaRepository<Ejemplar,Long> {
    List<Ejemplar> findByLibro_IsbnAndEstado(String isbn, EstadoEjemplar estado);
}
