package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.OrderDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.Order;

import java.util.List;

public interface IOrderService  {

    Order createOrder(OrderDTO orderDTO) throws DataNotFoundException;

    Order getOrder(Long id) throws DataNotFoundException ;

    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrder(Long id);

    List<Order> findByUserId(Long userId);

}
