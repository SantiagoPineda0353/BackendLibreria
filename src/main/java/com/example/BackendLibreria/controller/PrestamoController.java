package com.example.BackendLibreria.controller;

import com.example.BackendLibreria.dto.PrestamoRequestDto;
import com.example.BackendLibreria.dto.PrestamoResponseDto;
import com.example.BackendLibreria.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<PrestamoResponseDto> registrar(@Valid @RequestBody PrestamoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.registrarPrestamo(dto));
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResponseDto> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoResponseDto>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<PrestamoResponseDto>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(prestamoService.listarPorLibro(libroId));
    }
}