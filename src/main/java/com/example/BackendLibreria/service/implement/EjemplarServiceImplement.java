package com.example.BackendLibreria.service.implement;

import com.example.BackendLibreria.dto.EjemplarRequestDto;
import com.example.BackendLibreria.dto.EjemplarResponseDto;
import com.example.BackendLibreria.dto.LibroRequestDto;
import com.example.BackendLibreria.exception.BusinessException;
import com.example.BackendLibreria.exception.ResourceNotFoundException;
import com.example.BackendLibreria.model.Ejemplar;
import com.example.BackendLibreria.model.EstadoEjemplar;
import com.example.BackendLibreria.model.Libro;
import com.example.BackendLibreria.repository.EjemplarRepository;
import com.example.BackendLibreria.repository.LibroRepository;
import com.example.BackendLibreria.service.EjemplarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EjemplarServiceImplement implements EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    @Override
    public EjemplarResponseDto crear(EjemplarRequestDto dto) {
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(()-> new ResourceNotFoundException("No se encontro el libro con el id: " + dto.getLibroId()));

        boolean codigoExiste = ejemplarRepository.findAll().stream()
                .anyMatch(e->e.getCodigoInventario().equalsIgnoreCase(dto.getCodigoInventario()));

        if (codigoExiste){
            throw new BusinessException("Ya existe un ejemplar con el codigo de inventario" + dto.getCodigoInventario());
        }

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigoInventario(dto.getCodigoInventario());
        ejemplar.setEstado(EstadoEjemplar.DISPONIBLE);
        ejemplar.setLibro(libro);
        ejemplarRepository.save(ejemplar);

        Ejemplar guardado = ejemplarRepository.save(ejemplar);
        return toResponseDto(guardado);
    }

    @Override
    public List<EjemplarResponseDto> listarDisponiblesPorIsbn(String isbn) {
        return ejemplarRepository.findByLibro_IsbnAndEstado(isbn, EstadoEjemplar.DISPONIBLE).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public List<EjemplarResponseDto> listarPorLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontro el libro con id " + id);
        }
        return ejemplarRepository.findAll().stream()
                .filter(e -> e.getLibro().getId().equals(id))
                .map(this::toResponseDto)
                .toList();
    }

    private EjemplarResponseDto toResponseDto(Ejemplar ejemplar) {
        EjemplarResponseDto dto = new EjemplarResponseDto();
        dto.setId(ejemplar.getId());
        dto.setCodigoInventario(ejemplar.getCodigoInventario());
        dto.setEstado(ejemplar.getEstado());
        dto.setLibroId(ejemplar.getLibro().getId());
        dto.setTituloLibro(ejemplar.getLibro().getTitulo());
        dto.setIsbn(ejemplar.getLibro().getIsbn());
        return dto;
    }
}
