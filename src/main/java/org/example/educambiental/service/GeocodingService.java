package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeocodingService {

    private final RestTemplate restTemplate;
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";

    /**
     * Obtiene las coordenadas (lat, lon) de una dirección usando Nominatim (OSM).
     * @param direccion Dirección completa.
     * @return Arreglo con [latitud, longitud] o null si no se encuentra.
     */
    public Double[] obtenerCoordenadas(String direccion) {
        try {
            // Añadir "Ciudad de México" para mayor precisión si no la tiene
            if (!direccion.toLowerCase().contains("méxico")) {
                direccion += ", Ciudad de México, México";
            }

            String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_URL)
                    .queryParam("q", direccion)
                    .queryParam("format", "json")
                    .queryParam("limit", 1)
                    .toUriString();

            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response != null && !response.isEmpty()) {
                Map<String, Object> firstResult = response.get(0);
                Double lat = Double.parseDouble(firstResult.get("lat").toString());
                Double lon = Double.parseDouble(firstResult.get("lon").toString());
                return new Double[]{lat, lon};
            }
        } catch (Exception e) {
            log.error("Error geocodificando dirección {}: {}", direccion, e.getMessage());
        }
        return null;
    }
}
