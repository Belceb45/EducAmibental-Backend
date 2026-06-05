package org.example.educambiental.service.external;

import lombok.extern.slf4j.Slf4j;
import org.example.educambiental.dto.external.CdmxApiResponse;
import org.example.educambiental.entity.CentroReciclaje;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CdmxApiProvider implements CentroAcopioProvider {

    private final WebClient webClient;
    // Nueva API CKAN de Datos Abiertos CDMX
    private static final String API_BASE_URL = "https://datos.cdmx.gob.mx/api/3/action/datastore_search";
    private static final String RESOURCE_ID = "60e38ec4-7ac8-4d20-9256-49f2b86a7d1c";

    public CdmxApiProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_BASE_URL).build();
    }

    @Override
    public List<CentroReciclaje> obtenerCentros() {
        log.info("Iniciando consulta a la API CKAN de Datos Abiertos CDMX...");
        
        try {
            CdmxApiResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("resource_id", RESOURCE_ID)
                            .queryParam("limit", 50)
                            .build())
                    .retrieve()
                    .bodyToMono(CdmxApiResponse.class)
                    .block();

            if (response == null || !Boolean.TRUE.equals(response.getSuccess()) || 
                response.getResult() == null || response.getResult().getRecords() == null) {
                log.warn("La API de CDMX no devolvió resultados válidos.");
                return Collections.emptyList();
            }

            return response.getResult().getRecords().stream()
                    .map(this::mapToEntity)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al consumir la API de CDMX: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private CentroReciclaje mapToEntity(CdmxApiResponse.CdmxRecord record) {
        String direccion = String.format("%s, %s", 
                record.getEntidad() != null ? record.getEntidad() : "CDMX",
                record.getRegion() != null ? record.getRegion() : "");

        return CentroReciclaje.builder()
                .nombre(record.getNombre())
                .direccion(direccion)
                .horario("Consultar en sitio") // La nueva API no provee horario
                .contacto(record.getDescripcion() != null ? record.getDescripcion() : "No disponible")
                .latitud(record.getLatitud())
                .longitud(record.getLongitud())
                .capacidadLlena(false)
                .build();
    }
}
