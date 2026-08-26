package com.example.BackendLibreria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarRequestDto {

    @NotBlank(message = "El codigo de inventario es obligatorio")
    private String codigoInventario;

    @NotNull(message = "El id del libro es obligatorio")
    private Long libroId;
}
