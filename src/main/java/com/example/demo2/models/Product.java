package com.example.demo2.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id //Xac dinh Id la khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") // Trong truong hop nay thi ten id trong bang categories va bien id la giong nhau nen ta ko can them
    private Long id;

    @Column(name = "name", nullable = false, length = 350) // ko cho phep name la null, nay tuong tu voi bien name trong CategoryDTO va truong name trong bang categories, do dai maximum la 350 ki tu
    private String name;

    private Float price;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description")
    private String description;

    //created_at va updated_at ko can goi vi no da dat ke thua tu BaseEntity de keu 2 bien va su ly luon

    //category_id la Khoa ngoai nen phai su dung @ManyToOne
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
