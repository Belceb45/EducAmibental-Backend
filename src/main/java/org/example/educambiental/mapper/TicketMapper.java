package org.example.educambiental.mapper;

import org.example.educambiental.dto.TicketRequestDto;
import org.example.educambiental.dto.TicketResponseDto;
import org.example.educambiental.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "nombreUsuarioReporta", source = "usuarioReporta.nombre")
    @Mapping(target = "nombreAdminAsignado", source = "adminAsignado.nombre")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    @Mapping(target = "fechaActualizacion", source = "fechaActualizacion")
    TicketResponseDto toResponseDto(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "usuarioReporta", ignore = true)
    @Mapping(target = "adminAsignado", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Ticket toEntity(TicketRequestDto requestDto);
}
