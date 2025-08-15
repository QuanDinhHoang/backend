package com.example.demo2.services.Impl;

import com.example.demo2.dtos.BrandDTO;
import com.example.demo2.models.Brand;
import com.example.demo2.repositories.BrandRepository;
import com.example.demo2.services.interfaces.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;

    @Override
    public Brand createBrand(BrandDTO brandDTO) {
        if (brandRepository.existsByName(brandDTO.getName())) {
            throw new RuntimeException("Brand already exists");
        }
        Brand brand = Brand.builder()
                .name(brandDTO.getName())
                .brandImg(brandDTO.getBrandImg())
                .active(brandDTO.isActive())
                .build();
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        existingBrand.setName(brandDTO.getName());
        existingBrand.setBrandImg(brandDTO.getBrandImg());
        existingBrand.setActive(brandDTO.isActive());
        return brandRepository.save(existingBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        brandRepository.delete(brand);
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    
}
