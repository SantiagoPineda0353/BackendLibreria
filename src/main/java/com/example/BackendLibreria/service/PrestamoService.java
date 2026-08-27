package com.example.BackendLibreria.service;

import com.example.BackendLibreria.dto.PrestamoRequestDto;
import com.example.BackendLibreria.dto.PrestamoResponseDto;

import java.util.List;

public interface PrestamoService {
    PrestamoResponseDto registrarPrestamo(PrestamoRequestDto dto);
    PrestamoResponseDto devolverPrestamo(Long prestamoId);
    List<PrestamoResponseDto> listarPorUsuario(Long usuarioId);
    List<PrestamoResponseDto> listarPorLibro(Long libroId);
}