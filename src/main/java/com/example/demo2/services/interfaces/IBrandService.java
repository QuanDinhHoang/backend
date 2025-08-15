package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.BrandDTO;
import com.example.demo2.models.Brand;

import java.util.List;

public interface IBrandService {
    Brand createBrand(BrandDTO brandDTO);
    Brand updateBrand(Long id, BrandDTO brandDTO);
    void deleteBrand(Long id);
    Brand getBrandById(Long id);
    List<Brand> getAllBrands();
    
}
