package com.example.BackendLibreria.service.implement;

import com.example.BackendLibreria.dto.UsuarioRequestDto;
import com.example.BackendLibreria.dto.UsuarioResponseDto;
import com.example.BackendLibreria.exception.BusinessException;
import com.example.BackendLibreria.exception.ResourceNotFoundException;
import com.example.BackendLibreria.model.Usuario;
import com.example.BackendLibreria.repository.UsuarioRepository;
import com.example.BackendLibreria.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImplement implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDto crear(UsuarioRequestDto dto) {
        usuarioRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new BusinessException("El usuario ya existe con este Email: "+dto.getEmail());
        });

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());

        return toResponseDto(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioResponseDto> buscarTodos() {
        return usuarioRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    @Override
    public UsuarioResponseDto buscarPorId(Long id) {
        return toResponseDto(buscarUsuarioPorId(id));
    }

    @Override
    public UsuarioResponseDto actualizar(Long id, UsuarioRequestDto dto) {
        Usuario usuario = buscarUsuarioPorId(id);

        usuarioRepository.findByEmail(dto.getEmail()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new BusinessException("Ya existe otro usuario registrado con el email " + dto.getEmail());
            }
        });
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());

        return toResponseDto(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
    }

    private UsuarioResponseDto toResponseDto(Usuario usuario) {
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setId(usuario.getId());
        usuarioResponseDto.setNombre(usuario.getNombre());
        usuarioResponseDto.setApellido(usuario.getApellido());
        usuarioResponseDto.setEmail(usuario.getEmail());
        usuarioResponseDto.setFechaNacimiento(usuario.getFechaNacimiento());
        return usuarioResponseDto;
    }
}
