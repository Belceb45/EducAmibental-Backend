package org.example.educambiental.mapper;

import org.example.educambiental.dto.RecompensaRequestDto;
import org.example.educambiental.dto.RecompensaResponseDto;
import org.example.educambiental.entity.Recompensa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecompensaMapper {
    RecompensaResponseDto toResponseDto(Recompensa recompensa);

    @Mapping(target = "id", ignore = true)
    Recompensa toEntity(RecompensaRequestDto requestDto);
}
