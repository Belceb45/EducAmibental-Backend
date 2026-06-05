package org.example.educambiental.mapper;

import org.example.educambiental.dto.UsuarioRequestDto;
import org.example.educambiental.dto.UsuarioResponseDto;
import org.example.educambiental.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDto toResponseDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "puntosActuales", ignore = true)
    @Mapping(target = "nivelActual", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Usuario toEntity(UsuarioRequestDto requestDto);
}
