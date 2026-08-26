package com.example.BackendLibreria.controller;

import com.example.BackendLibreria.dto.LibroRequestDto;
import com.example.BackendLibreria.dto.LibroResponseDto;
import com.example.BackendLibreria.model.Libro;
import com.example.BackendLibreria.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PostMapping
    public ResponseEntity<LibroResponseDto> crear(@Valid @RequestBody LibroRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<LibroResponseDto>> buscarTodos(){
        return ResponseEntity.ok(libroService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(libroService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequestDto dto){
        return ResponseEntity.ok(libroService.actualizar(id,dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
