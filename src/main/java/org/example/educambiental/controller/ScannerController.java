package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.ScannerResponseDto;
import org.example.educambiental.service.ScannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scanner")
@RequiredArgsConstructor
public class ScannerController {

    private final ScannerService scannerService;

    @GetMapping("/{barcode}")
    public ResponseEntity<ScannerResponseDto> scanProduct(@PathVariable String barcode) {
        ScannerResponseDto result = scannerService.scanProduct(barcode);
        if (result.isEncontrado()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
