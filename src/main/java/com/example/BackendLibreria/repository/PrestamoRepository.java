package com.example.BackendLibreria.repository;

import com.example.BackendLibreria.model.EstadoPrestamo;
import com.example.BackendLibreria.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuario_Id(Long usuarioId);
    List<Prestamo> findByEjemplar_Libro_Id(Long libroId);
    boolean existsByUsuario_IdAndEstado(Long usuarioId, EstadoPrestamo estado);
}