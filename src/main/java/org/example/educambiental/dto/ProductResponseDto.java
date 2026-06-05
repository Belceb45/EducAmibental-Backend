package org.example.educambiental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDto {
    private String code;
    private ProductData product;
    private int status;
    @JsonProperty("status_verbose")
    private String statusVerbose;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductData {
        @JsonProperty("product_name")
        private String productName;
        
        private List<Packaging> packagings;
        
        @JsonProperty("packaging_text")
        private String packagingText;
        
        @JsonProperty("recycling_instructions_to_recycle")
        private String instructionsToRecycle;
        
        @JsonProperty("recycling_instructions_to_discard")
        private String instructionsToDiscard;
        
        @JsonProperty("image_url")
        private String imageUrl;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Packaging {
        private String material;
        private String shape;
        private String recycling;
    }
}
