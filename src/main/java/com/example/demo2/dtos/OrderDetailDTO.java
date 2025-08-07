package com.example.demo2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {

    @Min(value = 1, message = "Order id must be > 0")
    @JsonProperty("order_id")
    private Long orderId;

    @Min(value = 1, message = "Product id must be > 0")
    @JsonProperty("product_id")
    private Long productId;

    private Float price;

    @JsonProperty("total_of_products")
    private Integer totalOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;
}
