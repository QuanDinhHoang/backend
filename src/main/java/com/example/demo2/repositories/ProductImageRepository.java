package com.example.demo2.repositories;

import com.example.demo2.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository  extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
