package com.example.BackendLibreria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponseDto {
    private Long id;
    private String titulo;
    private String isbn;
    private String edicion;
    private LocalDate fechaPublicacion;
    private String autor;
    private int totalEjemplares;
}
