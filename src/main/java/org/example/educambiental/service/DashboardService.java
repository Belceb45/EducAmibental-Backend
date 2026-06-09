package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.AdminDashboardResponseDto;
import org.example.educambiental.entity.TicketEstado;
import org.example.educambiental.repository.CentroReciclajeRepository;
import org.example.educambiental.repository.ContenidoEstaticoRepository;
import org.example.educambiental.repository.MaterialRepository;
import org.example.educambiental.repository.TicketRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Métricas globales para el panel administrativo (RF19). Operado por ADMIN_SYSTEM vía API.
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final CentroReciclajeRepository centroReciclajeRepository;
    private final MaterialRepository materialRepository;
    private final ContenidoEstaticoRepository contenidoEstaticoRepository;
    private final TicketRepository ticketRepository;

    public AdminDashboardResponseDto obtenerMetricasAdmin() {
        Map<String, Long> ticketsPorEstado = new LinkedHashMap<>();
        for (TicketEstado estado : TicketEstado.values()) {
            ticketsPorEstado.put(estado.name(), ticketRepository.countByEstado(estado));
        }

        return AdminDashboardResponseDto.builder()
                .totalUsuarios(usuarioRepository.count())
                .usuariosVerificados(usuarioRepository.countByEnabledTrue())
                .totalCentros(centroReciclajeRepository.count())
                .totalMateriales(materialRepository.count())
                .totalContenidos(contenidoEstaticoRepository.count())
                .ticketsAbiertos(ticketRepository.countByEstado(TicketEstado.ABIERTO))
                .ticketsPorEstado(ticketsPorEstado)
                .build();
    }
}
