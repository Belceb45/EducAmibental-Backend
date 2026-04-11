package org.example.educambiental.mapper;

import org.example.educambiental.dto.CentroReciclajeRequestDto;
import org.example.educambiental.dto.CentroReciclajeResponseDto;
import org.example.educambiental.entity.CentroReciclaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CentroReciclajeMapper {
    CentroReciclajeResponseDto toResponseDto(CentroReciclaje centroReciclaje);

    @Mapping(target = "id", ignore = true)
    CentroReciclaje toEntity(CentroReciclajeRequestDto requestDto);
}
