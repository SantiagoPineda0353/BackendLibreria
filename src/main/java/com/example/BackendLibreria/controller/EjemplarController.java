package com.example.BackendLibreria.controller;

import com.example.BackendLibreria.dto.EjemplarRequestDto;
import com.example.BackendLibreria.dto.EjemplarResponseDto;
import com.example.BackendLibreria.model.Ejemplar;
import com.example.BackendLibreria.service.EjemplarService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
@RequiredArgsConstructor
public class EjemplarController {

    private final EjemplarService ejemplarService;

    @PostMapping
    public ResponseEntity<EjemplarResponseDto> crear(@RequestBody EjemplarRequestDto dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(ejemplarService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<EjemplarResponseDto>> listarDisponiblesPorIsbn(@RequestParam String isbn){
        return ResponseEntity.ok(ejemplarService.listarDisponiblesPorIsbn(isbn));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<EjemplarResponseDto>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(ejemplarService.listarPorLibro(libroId));
    }
}
