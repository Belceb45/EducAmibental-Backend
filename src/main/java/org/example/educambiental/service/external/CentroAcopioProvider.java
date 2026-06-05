package org.example.educambiental.service.external;

import org.example.educambiental.entity.CentroReciclaje;
import java.util.List;

/**
 * Interfaz estratégica para permitir múltiples proveedores de datos
 * (CDMX Open Data, Google Places, OSM, etc.)
 */
public interface CentroAcopioProvider {
    List<CentroReciclaje> obtenerCentros();
}
