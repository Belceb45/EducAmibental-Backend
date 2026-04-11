package org.example.educambiental.mapper;

import org.example.educambiental.dto.MaterialRequestDto;
import org.example.educambiental.dto.MaterialResponseDto;
import org.example.educambiental.entity.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoriaResiduoMapper.class})
public interface MaterialMapper {
    MaterialResponseDto toResponseDto(Material material);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria.id", source = "idCategoria")
    Material toEntity(MaterialRequestDto requestDto);
}
