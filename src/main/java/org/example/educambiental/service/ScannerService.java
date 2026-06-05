package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.ProductResponseDto;
import org.example.educambiental.dto.ScannerResponseDto;
import org.example.educambiental.entity.Material;
import org.example.educambiental.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScannerService {

    private final RestTemplate restTemplate;
    private final MaterialRepository materialRepository;
    
    private static final String OFF_API_URL = "https://world.openfoodfacts.org/api/v2/product/%s.json?fields=product_name,packagings,packaging_text,recycling_instructions_to_recycle,recycling_instructions_to_discard,image_url";

    public ScannerResponseDto scanProduct(String barcode) {
        String url = String.format(OFF_API_URL, barcode);
        
        try {
            ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);

            if (response == null || response.getStatus() == 0) {
                return ScannerResponseDto.builder().encontrado(false).build();
            }

            ProductResponseDto.ProductData product = response.getProduct();
            List<String> tagsMateriales = new ArrayList<>();
            
            if (product.getPackagings() != null) {
                tagsMateriales = product.getPackagings().stream()
                        .map(p -> p.getMaterial() != null ? p.getMaterial().toLowerCase() : "")
                        .filter(m -> !m.isEmpty())
                        .distinct()
                        .collect(Collectors.toList());
            }

            // Lógica de Mapeo Inteligente con nuestra DB
            Material materialEncontrado = buscarMaterialEnNuestraDB(tagsMateriales);
            
            String categoriaSugerida;
            String instrucciones;

            if (materialEncontrado != null) {
                categoriaSugerida = materialEncontrado.getCategoria().getNombre();
                instrucciones = materialEncontrado.getInstruccionesReciclaje();
            } else {
                // Fallback a lógica genérica si no hay match en DB
                categoriaSugerida = determinarCategoriaGenerica(tagsMateriales);
                instrucciones = product.getInstructionsToRecycle();
                if (instrucciones == null || instrucciones.isEmpty()) {
                    instrucciones = "Por favor, identifique el material visualmente y consulte nuestras guías generales de reciclaje.";
                }
            }

            return ScannerResponseDto.builder()
                    .nombreProducto(product.getProductName())
                    .imagenUrl(product.getImageUrl())
                    .materialesDetectados(tagsMateriales.stream().map(this::limpiarTag).collect(Collectors.toList()))
                    .instruccionesSugeridas(instrucciones)
                    .categoriaSugerida(categoriaSugerida)
                    .encontrado(true)
                    .build();

        } catch (Exception e) {
            return ScannerResponseDto.builder().encontrado(false).build();
        }
    }

    private Material buscarMaterialEnNuestraDB(List<String> tags) {
        if (tags.isEmpty()) return null;

        List<Material> todosLosMateriales = materialRepository.findAll();
        
        for (String tag : tags) {
            for (Material m : todosLosMateriales) {
                String nombreDB = m.getNombre().toLowerCase();
                // Match por términos clave (ej: "PET", "Glass", "Vidrio", "Plastic")
                if (tag.contains(nombreDB) || nombreDB.contains(limpiarTag(tag).toLowerCase())) {
                    return m;
                }
                
                // Mapeos específicos de OpenFoodFacts -> Nuestra DB
                if (tag.contains("polyethylene-terephthalate") && nombreDB.contains("pet")) return m;
                if (tag.contains("glass") && nombreDB.contains("vidrio")) return m;
                if (tag.contains("cardboard") && nombreDB.contains("cartón")) return m;
                if (tag.contains("aluminium") && nombreDB.contains("aluminio")) return m;
            }
        }
        return null;
    }

    private String limpiarTag(String tag) {
        if (tag == null) return "";
        return tag.replace("en:", "").replace("es:", "").replace("-", " ");
    }

    private String determinarCategoriaGenerica(List<String> materiales) {
        if (materiales.isEmpty()) return "Desconocido";
        for (String m : materiales) {
            if (m.contains("plastic") || m.contains("pet")) return "PLÁSTICO";
            if (m.contains("glass")) return "VIDRIO";
            if (m.contains("paper") || m.contains("cardboard")) return "PAPEL Y CARTÓN";
            if (m.contains("metal") || m.contains("aluminium")) return "METAL";
        }
        return "OTROS";
    }
}
