package com.example.demo2.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id //Xac dinh Id la khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") // Trong truong hop nay thi ten id trong bang categories va bien id la giong nhau nen ta ko can them
    private Long id;

    @Column(name = "name", nullable = false) // ko cho phep name la null, nay tuong tu voi bien name trong CategoryDTO va truong name trong bang categories
    private String name;


}
