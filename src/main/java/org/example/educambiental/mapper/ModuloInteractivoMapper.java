package org.example.educambiental.mapper;

import org.example.educambiental.dto.ModuloInteractivoRequestDto;
import org.example.educambiental.dto.ModuloInteractivoResponseDto;
import org.example.educambiental.entity.ModuloInteractivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ModuloInteractivoMapper {
    public abstract ModuloInteractivoResponseDto toResponseDto(ModuloInteractivo moduloInteractivo);

    @Mapping(target = "id", ignore = true)
    public abstract ModuloInteractivo toEntity(ModuloInteractivoRequestDto requestDto);
}
