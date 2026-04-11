package org.example.educambiental.mapper;

import org.example.educambiental.dto.CategoriaResiduoRequestDto;
import org.example.educambiental.dto.CategoriaResiduoResponseDto;
import org.example.educambiental.entity.CategoriaResiduo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaResiduoMapper {
    CategoriaResiduoResponseDto toResponseDto(CategoriaResiduo categoriaResiduo);

    @Mapping(target = "id", ignore = true)
    CategoriaResiduo toEntity(CategoriaResiduoRequestDto requestDto);
}
