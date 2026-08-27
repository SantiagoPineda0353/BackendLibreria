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
@Table(name="usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private String apellido;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(name="fecha_nacimiento", nullable=false)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "usuario")
    private List<Prestamo> prestamos = new ArrayList<>();

}
