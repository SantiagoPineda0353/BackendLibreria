package com.example.BackendLibreria.dto;

import com.example.BackendLibreria.model.EstadoEjemplar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EjemplarResponseDto {
    private Long id;
    private String codigoInventario;
    private EstadoEjemplar estado;
    private Long libroId;
    private String tituloLibro;
    private String isbn;
}
