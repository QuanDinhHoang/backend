package com.example.demo2.responses;

import com.example.demo2.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//@Data //Se thuc hien toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
@Builder
public class ProductResponse extends BaseResponse{

    private String name;

    private float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id") // neu ta muon su dung ten ta tu dat ma no ko giong voi thuoc tinh trong table thi phai su dung @JsonProperty, neu giong nhau thi ko su dung
    private Long categoryId;


    public static ProductResponse fromProduct(Product product){
        ProductResponse productResponse=  ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreateAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
