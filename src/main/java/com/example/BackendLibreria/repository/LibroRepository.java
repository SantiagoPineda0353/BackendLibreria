package com.example.BackendLibreria.repository;

import com.example.BackendLibreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByIsbn(String isbn);
}
