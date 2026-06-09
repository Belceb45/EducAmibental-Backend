package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educambiental.entity.CategoriaResiduo;
import org.example.educambiental.entity.CentroReciclaje;
import org.example.educambiental.repository.CategoriaResiduoRepository;
import org.example.educambiental.repository.CentroReciclajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CentroImportService {

    private final CentroReciclajeRepository centroRepository;
    private final CategoriaResiduoRepository categoriaRepository;
    private final GeocodingService geocodingService;

    @Transactional
    public void importarDesdeCSV(String filePath) {
        log.info("Iniciando importación masiva de centros desde: {}", filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int importados = 0;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                // Saltar líneas vacías o de formato inicial
                if (line.trim().isEmpty() || line.startsWith(",,,,,")) continue;
                if (line.contains("ID,Centro de Reciclaje")) continue;

                // Split por comas fuera de comillas
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (parts.length < 6) continue;

                // Limpiar comillas
                String nombre = parts[2].replace("\"", "").trim();
                String ubicacion = parts[3].replace("\"", "").trim();
                String telefono = parts[4].replace("\"", "").trim();
                String materialesStr = parts[5].replace("\"", "").trim();

                if (centroRepository.existsByNombreAndDireccion(nombre, ubicacion)) {
                    continue;
                }

                // Geocodificación (Nota: Nominatim permite 1 req/sec)
                // Para no bloquear, en un entorno real esto sería asíncrono
                Double[] coords = geocodingService.obtenerCoordenadas(ubicacion);
                
                CentroReciclaje centro = CentroReciclaje.builder()
                        .nombre(nombre)
                        .direccion(ubicacion)
                        .contacto(telefono)
                        .horario("No especificado")
                        .descripcion(materialesStr)
                        .latitud(coords != null ? coords[0] : null)
                        .longitud(coords != null ? coords[1] : null)
                        .materialesAceptados(mapearMateriales(materialesStr))
                        .build();

                centroRepository.save(centro);
                importados++;
                
                // Pequeño delay para respetar el User-Agent y rate limit de OSM
                Thread.sleep(1000); 
                
                if (importados % 10 == 0) {
                    log.info("Procesados {} centros...", importados);
                }
            }
            log.info("Importación finalizada. Centros nuevos: {}", importados);

        } catch (Exception e) {
            log.error("Error durante la importación: {}", e.getMessage());
        }
    }

    private List<CategoriaResiduo> mapearMateriales(String materialesStr) {
        List<CategoriaResiduo> categorias = new ArrayList<>();
        String[] materiales = materialesStr.split(",");
        
        for (String m : materiales) {
            String nombreLimpio = m.trim().split("\\(")[0].trim(); // Quitar "(Compra)", etc.
            Optional<CategoriaResiduo> cat = categoriaRepository.findAll().stream()
                    .filter(c -> c.getNombre().equalsIgnoreCase(nombreLimpio))
                    .findFirst();
            
            if (cat.isPresent()) {
                categorias.add(cat.get());
            } else {
                // Crear categoría si no existe para mantener la data íntegra
                CategoriaResiduo nueva = CategoriaResiduo.builder()
                        .nombre(nombreLimpio)
                        .descripcion("Categoría importada automáticamente")
                        .build();
                categorias.add(categoriaRepository.save(nueva));
            }
        }
        return categorias;
    }
}
