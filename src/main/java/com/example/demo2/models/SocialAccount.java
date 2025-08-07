package com.example.demo2.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_accounts")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAccount {
    @Id //Xac dinh Id la khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") // Trong truong hop nay thi ten id trong bang categories va bien id la giong nhau nen ta ko can them
    private Long id;

    @Column(name = "provider", length = 20, nullable = false)
    private String provider;

    @Column(name = "provider_id", length = 50, nullable = false)
    private Long providerId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    //bang nay ko quan trong lam nen co the them no ko du giong nhu table
}
