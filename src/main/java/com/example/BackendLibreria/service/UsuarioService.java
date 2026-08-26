package com.example.BackendLibreria.service;

import com.example.BackendLibreria.dto.UsuarioRequestDto;
import com.example.BackendLibreria.dto.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDto crear(UsuarioRequestDto dto);
    List<UsuarioResponseDto> buscarTodos();
    UsuarioResponseDto buscarPorId(Long id);
    UsuarioResponseDto actualizar(Long id, UsuarioRequestDto dto);
    void eliminar(Long id);
}
