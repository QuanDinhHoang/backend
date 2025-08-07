package com.example.demo2.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;

    @Id //Xac dinh Id la khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") // Trong truong hop nay thi ten id trong bang categories va bien id la giong nhau nen ta ko can them
    private Long id;

    //product_id la Khoa ngoai nen phai su dung @ManyToOne
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_url", length = 300)
    private String imageU;

}
