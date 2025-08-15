package com.example.demo2.controllers;

import com.example.demo2.dtos.BrandDTO;
import com.example.demo2.models.Brand;
import com.example.demo2.services.interfaces.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandService brandService;

    @PostMapping("")
    public ResponseEntity<Brand> createBrand(@Valid @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.createBrand(brandDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id,
                                             @Valid @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Brand deleted successfully");
    }


}
