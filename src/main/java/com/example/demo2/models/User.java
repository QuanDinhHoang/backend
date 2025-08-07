package com.example.demo2.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id //Xac dinh Id la khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") // Trong truong hop nay thi ten id trong bang categories va bien id la giong nhau nen ta ko can them
    private Long id;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    //created_at va updated_at da dc tao bang cach ke thua tu BaseEntity

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
