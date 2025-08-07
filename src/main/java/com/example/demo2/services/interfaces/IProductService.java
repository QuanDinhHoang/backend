package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.ProductDTO;
import com.example.demo2.dtos.ProductImageDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.exceptions.InvalidParamException;
import com.example.demo2.models.Product;
import com.example.demo2.models.ProductImage;
import com.example.demo2.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

public interface IProductService {
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(long id);

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException; //Can phan trang vi so luong no nhieu

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException;


}





