package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.entity.Material;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.MaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    public Page<Material> listarTodos(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    public Material crearMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material actualizarMaterial(Long id, Material detalles) {
        Material material = obtenerPorId(id);
        material.setNombre(detalles.getNombre());
        material.setInstruccionesReciclaje(detalles.getInstruccionesReciclaje());
        material.setCategoria(detalles.getCategoria());
        return materialRepository.save(material);
    }

    public void eliminarMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Material no encontrado.");
        }
        materialRepository.deleteById(id);
    }

    public Material obtenerPorId(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado con ID: " + id));
    }
}
