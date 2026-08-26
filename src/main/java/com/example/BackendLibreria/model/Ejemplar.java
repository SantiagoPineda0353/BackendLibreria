package com.example.BackendLibreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="ejemplares")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Ejemplar {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="codigo_inventario",nullable = false,unique = true)
    private String codigoInventario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEjemplar estado= EstadoEjemplar.DISPONIBLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="libro_id", nullable = false)
    private Libro libro;
}
