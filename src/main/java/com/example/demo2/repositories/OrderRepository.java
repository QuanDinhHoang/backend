package com.example.demo2.repositories;

import com.example.demo2.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tim cac don hang cua user nao do

    List<Order> findByUserId(Long userId);


}
