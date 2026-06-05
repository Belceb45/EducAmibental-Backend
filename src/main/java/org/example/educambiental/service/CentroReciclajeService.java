package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educambiental.entity.CentroReciclaje;
import org.example.educambiental.repository.CentroReciclajeRepository;
import org.example.educambiental.service.external.CentroAcopioProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.example.educambiental.exception.ResourceNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CentroReciclajeService {

    private final CentroReciclajeRepository repository;
    private final CentroAcopioProvider cdmxProvider;

    /**
     * Sincroniza los centros de acopio desde la fuente externa.
     * Se ejecuta automáticamente cada lunes a la medianoche.
     */
    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void sincronizarCentrosExternos() {
        log.info("Iniciando sincronización semanal de centros de acopio...");
        
        List<CentroReciclaje> centrosNuevos = cdmxProvider.obtenerCentros();
        
        for (CentroReciclaje centro : centrosNuevos) {
            // Evitar duplicados basados en nombre y coordenadas
            if (!repository.existsByNombreAndLatitudAndLongitud(centro.getNombre(), centro.getLatitud(), centro.getLongitud())) {
                repository.save(centro);
                log.debug("Centro guardado: {}", centro.getNombre());
            }
        }
        
        log.info("Sincronización completada. Total procesados: {}", centrosNuevos.size());
    }

    public List<CentroReciclaje> obtenerTodos() {
        return repository.findAll();
    }

    public CentroReciclaje crearCentro(CentroReciclaje centro) {
        return repository.save(centro);
    }

    public CentroReciclaje actualizarCentro(Long id, CentroReciclaje detalles) {
        CentroReciclaje centro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Centro de reciclaje no encontrado."));
        
        centro.setNombre(detalles.getNombre());
        centro.setLatitud(detalles.getLatitud());
        centro.setLongitud(detalles.getLongitud());
        centro.setDireccion(detalles.getDireccion());
        centro.setHorario(detalles.getHorario());
        centro.setContacto(detalles.getContacto());
        centro.setCapacidadLlena(detalles.getCapacidadLlena());
        
        return repository.save(centro);
    }

    public void eliminarCentro(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Centro de reciclaje no encontrado.");
        }
        repository.deleteById(id);
    }
}
