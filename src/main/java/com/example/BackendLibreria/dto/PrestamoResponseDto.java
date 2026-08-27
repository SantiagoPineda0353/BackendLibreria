package com.example.BackendLibreria.dto;

import com.example.BackendLibreria.model.EstadoPrestamo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponseDto {
    private Long id;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;

    private Long usuarioId;
    private String nombreUsuario;

    private Long ejemplarId;
    private String codigoInventario;
    private Long libroId;
    private String tituloLibro;
    private String isbn;
}