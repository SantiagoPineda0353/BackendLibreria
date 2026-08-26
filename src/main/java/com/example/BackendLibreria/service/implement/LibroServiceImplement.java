package com.example.BackendLibreria.service.implement;

import com.example.BackendLibreria.dto.LibroRequestDto;
import com.example.BackendLibreria.dto.LibroResponseDto;
import com.example.BackendLibreria.exception.BusinessException;
import com.example.BackendLibreria.exception.ResourceNotFoundException;
import com.example.BackendLibreria.model.Libro;
import com.example.BackendLibreria.repository.LibroRepository;
import com.example.BackendLibreria.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroServiceImplement implements LibroService {

    private final LibroRepository libroRepository;

    @Override
    public LibroResponseDto crear(LibroRequestDto dto) {
        libroRepository.findByIsbn(dto.getIsbn()).ifPresent(l -> {
            throw new BusinessException("Ya existe un libro registrado con el ISBN " + dto.getIsbn());
        });

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setIsbn(dto.getIsbn());
        libro.setEdicion(dto.getEdicion());
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setAutor(dto.getAutor());

        Libro guardado = libroRepository.save(libro);
        return toResponseDto(guardado);
    }

    @Override
    public List<LibroResponseDto> buscarTodos() {
        return libroRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public LibroResponseDto buscarPorId(Long id) {
        Libro libro = buscarLibroPorId(id);
        return toResponseDto(libro);
    }

    @Override
    public LibroResponseDto actualizar(Long id, LibroRequestDto dto) {
        Libro libro = buscarLibroPorId(id);

        libroRepository.findByIsbn(dto.getIsbn()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new BusinessException("Ya existe otro libro registrado con el ISBN " + dto.getIsbn());
            }
        });

        libro.setTitulo(dto.getTitulo());
        libro.setIsbn(dto.getIsbn());
        libro.setEdicion(dto.getEdicion());
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setAutor(dto.getAutor());

        return toResponseDto(libroRepository.save(libro));
    }

    @Override
    public void eliminar(Long id) {
        Libro libro = buscarLibroPorId(id); // Pendiente para colocar una validacion que diga que si esta seguro, ya que  tambien elimina los ejemplares ya que estan en cadena
        libroRepository.delete(libro);
    }

    private Libro buscarLibroPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el libro con id " + id));
    }

    private LibroResponseDto toResponseDto(Libro libro) {
        LibroResponseDto dto = new LibroResponseDto();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setIsbn(libro.getIsbn());
        dto.setEdicion(libro.getEdicion());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        dto.setAutor(libro.getAutor());
        dto.setTotalEjemplares(libro.getEjemplares().size());
        return dto;
    }
}
