package com.example.demo2.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    int totalPages;

}
