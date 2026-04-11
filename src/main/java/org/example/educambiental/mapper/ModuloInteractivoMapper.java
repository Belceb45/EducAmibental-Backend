package org.example.educambiental.mapper;

import org.example.educambiental.dto.ModuloInteractivoRequestDto;
import org.example.educambiental.dto.ModuloInteractivoResponseDto;
import org.example.educambiental.entity.ModuloInteractivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuloInteractivoMapper {
    ModuloInteractivoResponseDto toResponseDto(ModuloInteractivo moduloInteractivo);

    @Mapping(target = "id", ignore = true)
    ModuloInteractivo toEntity(ModuloInteractivoRequestDto requestDto);
}
