package com.example.demo2.repositories;

import com.example.demo2.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Kiem tra co ton tai san pham nao voi ten do ko
    boolean existsByName(String name);

    @Override
    Page<Product> findAll(Pageable pageable); //phan trang
}
