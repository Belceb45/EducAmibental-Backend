package org.example.educambiental.mapper;

import org.example.educambiental.dto.CodigoDescuentoRequestDto;
import org.example.educambiental.dto.CodigoDescuentoResponseDto;
import org.example.educambiental.entity.CodigoDescuento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RecompensaMapper.class, UsuarioMapper.class})
public interface CodigoDescuentoMapper {
    CodigoDescuentoResponseDto toResponseDto(CodigoDescuento codigoDescuento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recompensa.id", source = "idRecompensa")
    @Mapping(target = "usuario.id", source = "idUsuario")
    CodigoDescuento toEntity(CodigoDescuentoRequestDto requestDto);
}
