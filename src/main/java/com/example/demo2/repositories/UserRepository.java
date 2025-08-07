package com.example.demo2.repositories;

import com.example.demo2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Kiem tra xem co ton tai user co phone number nay ko
    boolean existsByPhoneNumber(String phoneNumber);

    // Tuong duong: SELECT * FROM users WHERE phone_number = ?
    Optional<User> findByPhoneNumber(String phoneNumber);
}
