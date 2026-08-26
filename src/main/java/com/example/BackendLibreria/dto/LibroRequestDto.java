package com.example.BackendLibreria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequestDto {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    private String edicion;

    private LocalDate fechaPublicacion;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;
}
