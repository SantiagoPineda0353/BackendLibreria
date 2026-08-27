package com.example.BackendLibreria.service.implement;

import com.example.BackendLibreria.dto.PrestamoRequestDto;
import com.example.BackendLibreria.dto.PrestamoResponseDto;
import com.example.BackendLibreria.exception.BusinessException;
import com.example.BackendLibreria.exception.ResourceNotFoundException;
import com.example.BackendLibreria.model.*;
import com.example.BackendLibreria.repository.EjemplarRepository;
import com.example.BackendLibreria.repository.LibroRepository;
import com.example.BackendLibreria.repository.PrestamoRepository;
import com.example.BackendLibreria.repository.UsuarioRepository;
import com.example.BackendLibreria.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImplement implements PrestamoService {

    private static final int DIAS_PRESTAMO_DEFAULT = 15;

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final EjemplarRepository ejemplarRepository;

    @Override
    public PrestamoResponseDto registrarPrestamo(PrestamoRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el usuario con id " + dto.getUsuarioId()));

        Libro libro = libroRepository.findByIsbn(dto.getIsbn())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro un libro con ISBN " + dto.getIsbn()));

        if (prestamoRepository.existsByUsuario_IdAndEstado(usuario.getId(), EstadoPrestamo.ACTIVO)) {
            throw new BusinessException("El usuario ya tiene un prestamo activo. Debe devolverlo antes de solicitar otro.");
        }

        Ejemplar ejemplar = ejemplarRepository.findByLibro_IsbnAndEstado(dto.getIsbn(), EstadoEjemplar.DISPONIBLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("No hay ejemplares disponibles para el libro con ISBN " + dto.getIsbn()));

        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucionEsperada = dto.getFechaDevolucionEsperada() != null
                ? dto.getFechaDevolucionEsperada()
                : fechaPrestamo.plusDays(DIAS_PRESTAMO_DEFAULT);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucionEsperada(fechaDevolucionEsperada);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        ejemplar.setEstado(EstadoEjemplar.PRESTAMO);
        ejemplarRepository.save(ejemplar);

        Prestamo guardado = prestamoRepository.save(prestamo);
        return toResponseDTO(guardado);
    }

    @Override
    public PrestamoResponseDto devolverPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el prestamo con id " + prestamoId));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BusinessException("Este prestamo ya fue devuelto anteriormente");
        }

        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setEstado(EstadoEjemplar.DISPONIBLE);
        ejemplarRepository.save(ejemplar);

        return toResponseDTO(prestamoRepository.save(prestamo));
    }

    @Override
    public List<PrestamoResponseDto> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("No se encontro el usuario con id " + usuarioId);
        }
        return prestamoRepository.findByUsuario_Id(usuarioId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<PrestamoResponseDto> listarPorLibro(Long libroId) {
        if (!libroRepository.existsById(libroId)) {
            throw new ResourceNotFoundException("No se encontro el libro con id " + libroId);
        }
        return prestamoRepository.findByEjemplar_Libro_Id(libroId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private EstadoPrestamo calcularEstadoActual(Prestamo prestamo) {
        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            return EstadoPrestamo.DEVUELTO;
        }
        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionEsperada())) {
            return EstadoPrestamo.VENCIDO;
        }
        return EstadoPrestamo.ACTIVO;
    }

    private PrestamoResponseDto toResponseDTO(Prestamo prestamo) {
        EstadoPrestamo estadoActual = calcularEstadoActual(prestamo);

        if (estadoActual != prestamo.getEstado()) {
            prestamo.setEstado(estadoActual);
            prestamoRepository.save(prestamo);
        }

        PrestamoResponseDto dto = new PrestamoResponseDto();
        dto.setId(prestamo.getId());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada());
        dto.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        dto.setEstado(estadoActual);

        dto.setUsuarioId(prestamo.getUsuario().getId());
        dto.setNombreUsuario(prestamo.getUsuario().getNombre() + " " + prestamo.getUsuario().getApellido());

        dto.setEjemplarId(prestamo.getEjemplar().getId());
        dto.setCodigoInventario(prestamo.getEjemplar().getCodigoInventario());
        dto.setLibroId(prestamo.getEjemplar().getLibro().getId());
        dto.setTituloLibro(prestamo.getEjemplar().getLibro().getTitulo());
        dto.setIsbn(prestamo.getEjemplar().getLibro().getIsbn());

        return dto;
    }
}