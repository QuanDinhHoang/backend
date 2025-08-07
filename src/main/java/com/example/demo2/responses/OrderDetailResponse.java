package com.example.demo2.responses;

import com.example.demo2.models.Order;
import com.example.demo2.models.OrderDetail;
import com.example.demo2.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
@Builder
public class OrderDetailResponse {
    private Long id;

    @JoinColumn(name = "order_id")
    private Long orderId;

    @JoinColumn(name = "product_id")
    private Long productId;

    @Column(name = "price")
    private Float price;

    @Column(name = "number_of_products")
    private int numberOfProducts;

    @Column(name = "total_money")
    private Float totalMoney;

    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        OrderDetailResponse orderDetailResponse=  OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();

        return orderDetailResponse;
    }

}
