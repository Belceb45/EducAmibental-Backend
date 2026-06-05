package org.example.educambiental.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CdmxApiResponse {
    private Boolean success;
    private CdmxResult result;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CdmxResult {
        private List<CdmxRecord> records;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CdmxRecord {
        @JsonProperty("nombre")
        private String nombre;
        
        @JsonProperty("latitud")
        private Double latitud;
        
        @JsonProperty("longitud")
        private Double longitud;

        @JsonProperty("entidad")
        private String entidad;

        @JsonProperty("region")
        private String region;

        @JsonProperty("descripcio")
        private String descripcion;
    }
}
