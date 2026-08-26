package com.example.BackendLibreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Libro {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String titulo;

    @Column(nullable=false)
    private String isbn;

    @Column(nullable=false)
    private String autor;

    private String edicion;

    @Column(name="fecha_publicacion")
    private LocalDate fechaPublicacion;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejemplar> ejemplares=new ArrayList<>();

}
