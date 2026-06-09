package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.CentroReciclajeRequestDto;
import org.example.educambiental.dto.CentroReciclajeResponseDto;
import org.example.educambiental.mapper.CentroReciclajeMapper;
import org.example.educambiental.service.CentroImportService;
import org.example.educambiental.service.CentroReciclajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/centros")
@RequiredArgsConstructor
public class CentroReciclajeController {

    private final CentroReciclajeService centroReciclajeService;
    private final CentroReciclajeMapper centroReciclajeMapper;
    private final CentroImportService centroImportService;

    /**
     * Obtiene todos los centros de reciclaje registrados.
     * Útil para mostrar en el mapa del frontend.
     */
    @GetMapping
    public ResponseEntity<List<CentroReciclajeResponseDto>> obtenerTodos() {
        List<CentroReciclajeResponseDto> centros = centroReciclajeService.obtenerTodos()
                .stream()
                .map(centroReciclajeMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(centros);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<CentroReciclajeResponseDto> crear(@Valid @RequestBody CentroReciclajeRequestDto requestDto) {
        return ResponseEntity.ok(centroReciclajeMapper.toResponseDto(
                centroReciclajeService.crearCentro(centroReciclajeMapper.toEntity(requestDto))
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<CentroReciclajeResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody CentroReciclajeRequestDto requestDto) {
        return ResponseEntity.ok(centroReciclajeMapper.toResponseDto(
                centroReciclajeService.actualizarCentro(id, centroReciclajeMapper.toEntity(requestDto))
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        centroReciclajeService.eliminarCentro(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Dispara manualmente la sincronización con la API de Datos Abiertos de la CDMX.
     * Solo accesible para administradores.
     */
    @PostMapping("/sincronizar")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<Map<String, String>> sincronizar() {
        centroReciclajeService.sincronizarCentrosExternos();
        return ResponseEntity.ok(Map.of("message", "Sincronización manual iniciada y completada exitosamente."));
    }

    /**
     * Importa centros desde el archivo CSV local en requirements/.
     */
    @PostMapping("/importar-csv")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<Map<String, String>> importarCsv() {
        String path = "requirements/CentrosReciclaje - Hoja 1.csv";
        centroImportService.importarDesdeCSV(path);
        return ResponseEntity.ok(Map.of("message", "Proceso de importación de CSV iniciado (revisa los logs para el progreso)."));
    }
}
