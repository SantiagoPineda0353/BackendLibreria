package com.example.BackendLibreria.service;

import com.example.BackendLibreria.dto.LibroRequestDto;
import com.example.BackendLibreria.dto.LibroResponseDto;

import java.util.List;

public interface LibroService {
    LibroResponseDto crear(LibroRequestDto dto);
    List<LibroResponseDto> buscarTodos();
    LibroResponseDto buscarPorId(Long id);
    LibroResponseDto actualizar(Long id, LibroRequestDto dto);
    void eliminar(Long id);
}
