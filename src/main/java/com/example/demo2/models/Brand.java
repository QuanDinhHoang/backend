package com.example.demo2.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_img", length = 500)
    private String brandImg;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, length = 100)
    private String name;

    // Một brand có thể có nhiều sản phẩm
    //@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Product> products;
}

