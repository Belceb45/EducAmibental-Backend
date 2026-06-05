package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario crearUsuario(Usuario usuario) {
        // Por defecto, nuevos usuarios son "USER" a menos que se especifique lo contrario
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarUsuario(UUID idUsuarioAEliminar) {
        if (!usuarioRepository.existsById(idUsuarioAEliminar)) {
            throw new ResourceNotFoundException("Usuario a eliminar no encontrado.");
        }

        usuarioRepository.deleteById(idUsuarioAEliminar);
    }

    @Transactional
    public void eliminarUsuarioAutenticado() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado no encontrado."));
        
        usuarioRepository.delete(usuario);
    }
}
