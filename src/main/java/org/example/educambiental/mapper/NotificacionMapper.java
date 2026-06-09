package org.example.educambiental.mapper;

import org.example.educambiental.dto.NotificacionResponseDto;
import org.example.educambiental.entity.Notificacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {
    NotificacionResponseDto toResponseDto(Notificacion notificacion);
}
