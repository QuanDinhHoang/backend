package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.OrderDetailDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.OrderDetail;
import com.example.demo2.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteOrderDetail(Long id); //xoa cung luon

    List<OrderDetail> getOrderDetails(Long orderId);


}
