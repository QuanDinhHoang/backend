package com.example.demo2.controllers;

import com.example.demo2.dtos.CategoryDTO;
import com.example.demo2.dtos.ProductDTO;
//import com.example.demo2.services.Impl.ProductService;
import com.example.demo2.dtos.ProductImageDTO;
import com.example.demo2.models.Product;
import com.example.demo2.models.ProductImage;
import com.example.demo2.responses.ProductListResponse;
import com.example.demo2.responses.ProductResponse;
import com.example.demo2.services.interfaces.IProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("") //http://localhost:8080/api/v1/products?page=1&limit=10
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam("page") int page,   //Su dung trong phan trang , phan bao nhieu trang, moi trang gioi han bao nhieu , @RequestParam("page") la tham so gui qua tu trinh duyet web
            @RequestParam("limit") int limit
    ){
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("createdAt").descending()
        );

        // Gọi service để lấy danh sách sản phẩm phân trang
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        //Lay tong so trang
        int totalPages = productPage.getTotalPages();

        List<ProductResponse> products = productPage.getContent();

        return ResponseEntity.ok(ProductListResponse.builder()
                        .products(products)
                        .totalPages(totalPages)
                .build());
    }

    @PostMapping("")
    //Neu tham so truyen vao la 1 object(JSON) thi phai su dung Data Transfer Object(hay DTO) de bien JSON thanh object truyen vao
    //@RequestBody giup doc lay noi dung trong JSON
    //@Valid de co the su dung dc @NotEmpty trong CategoryDTO
    public ResponseEntity<?> createProduct( // ResponseEntity<?> phai de la ? boi ve ket qua return co the la String hoac la List
                                             @Valid @RequestBody ProductDTO productDTO, //@ModelAttribute dc su dung khi Dữ liệu gửi lên từ form HTML (application/x-www-form-urlencoded hoặc multipart/form-data). Có upload file (MultipartFile)
                                             //@RequestPart("file") MultipartFile file, //Dùng để lấy một phần của multipart request, cụ thể là phần có tên "file". Thường dùng trong các API có kiểu multipart/form-data, ví dụ như upload file kèm theo dữ liệu JSON.
                                            //@ModelAttribute("files") List<MultipartFile> files,
                                            BindingResult result){ //Them moi 1 ban ghi category
        try{
            if(result.hasErrors()){
                List<String> errorMessages =result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages); // it will return list
            }
            Product newProduct = productService.createProduct(productDTO);


            return ResponseEntity.ok("create product successfully " + newProduct); // it will return string
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFiles(
            @ModelAttribute("files") List<MultipartFile> files,
            @PathVariable("id") Long productId){

        try{
            Product existingProduct = productService.getProductById(productId);

            if(files == null){
                files = new ArrayList<MultipartFile>();
            }

            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }

            List<ProductImage> productImages = new ArrayList<>();
            for(MultipartFile file : files){
                if (file.getSize() == 0){
                    continue;
                }

                //Kiem tra kich thuoc file va dinh dang
                if (file.getSize() > 10 * 1024 * 1024) { // Kiem tra neu Kích thước > 10MB
                    throw new ResponseStatusException(
                            HttpStatus.PAYLOAD_TOO_LARGE, "File is too large! Maximum size is 10MB");
                }

                //Lay dinh dang file anh
                String contentType = file.getContentType();
                //Kiem tra cai link bo vo JSON co phai file anh ko
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }

                //Luu file va cap nhat thumbnail trong ProductDTO
                String fileName = storeFile(file);
                //Luu vao doi tuong product trong DB
                //Thuc chat la luu vao bang product_images
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .productId(existingProduct.getId())
                                .imageUrl(fileName)
                                .build()
                );
                productImages.add(productImage);

            }
            return ResponseEntity.ok().body(productImages);

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //luu tru file da upload len tren json
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }

        // Lấy tên file gốc và làm sạch đường dẫn
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads"); // luu vao folder uploads cua project nay

        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }


    @GetMapping("/{id}")
    //Lay 1 product bang id, co nghia ta truyen Id thi ta se lay dc product
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId){

        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
            @RequestBody ProductDTO productDTO
    ){
        try {
            Product existingProduct= productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("This is delete product with id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //@PostMapping("/generateFakeProducts")
    public ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();

        for (int i = 0; i < 1_000; i++) {
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }

            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10, 90_000_000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long) faker.number().numberBetween(2, 5))
                    .build();

            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.ok("Fake Products created successfully");
    }

}
