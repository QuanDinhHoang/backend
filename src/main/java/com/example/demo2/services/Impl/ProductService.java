package com.example.demo2.services.Impl;

import com.example.demo2.dtos.ProductDTO;
import com.example.demo2.dtos.ProductImageDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.exceptions.InvalidParamException;
import com.example.demo2.models.Category;
import com.example.demo2.models.Product;
import com.example.demo2.models.ProductImage;
import com.example.demo2.repositories.CategoryReposity;
import com.example.demo2.repositories.ProductImageRepository;
import com.example.demo2.repositories.ProductRepository;
import com.example.demo2.responses.ProductResponse;
import com.example.demo2.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryReposity categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory= categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Can not find category with id " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page) và giới hạn (limit)
        return productRepository.findAll(pageRequest)
                .map(product -> {
                    return ProductResponse.fromProduct(product);
                });
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existingProduct = getProductById(id); //Kiem tra Product id co ton tai hay ko
        if(existingProduct != null) {
            //copy cac thuoc tinh tu DTO → Product
            //C6 the su dung ModelMapper
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find category with id: "+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId,
                                           ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException{
        Product existingProduct = productRepository.findById(productImageDTO.getProductId()).orElseThrow(() -> new DataNotFoundException("Can not find product with id" + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageU(productImageDTO.getImageUrl())
                .build();

        //Ko cho insert qua 5 anh cho 1 san pham
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
