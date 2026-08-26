package com.example.BackendLibreria.service;

import com.example.BackendLibreria.dto.EjemplarRequestDto;
import com.example.BackendLibreria.dto.EjemplarResponseDto;
import com.example.BackendLibreria.dto.LibroRequestDto;

import java.util.List;

public interface EjemplarService {
    EjemplarResponseDto crear(EjemplarRequestDto dto);
    List<EjemplarResponseDto> listarDisponiblesPorIsbn(String isbn);
    List<EjemplarResponseDto> listarPorLibro(Long id);
}
