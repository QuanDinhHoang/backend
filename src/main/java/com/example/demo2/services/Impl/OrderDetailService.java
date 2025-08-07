package com.example.demo2.services.Impl;

import com.example.demo2.dtos.OrderDTO;
import com.example.demo2.dtos.OrderDetailDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.Order;
import com.example.demo2.models.OrderDetail;
import com.example.demo2.models.Product;
import com.example.demo2.repositories.OrderDetailRepository;
import com.example.demo2.repositories.OrderRepository;
import com.example.demo2.repositories.ProductRepository;
import com.example.demo2.responses.OrderDetailResponse;
import com.example.demo2.services.interfaces.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //Tim xem orderId co ton tai hay khong
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(() -> new DataNotFoundException("Can not find an order with id" + orderDetailDTO.getOrderId()));

        //Tim xem productId co ton tai hay khong
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(() -> new DataNotFoundException("Can not find a Product with id " + orderDetailDTO.getProductId()));

        OrderDetail newOrderDetail = OrderDetail.builder()
                .order(existingOrder)
                .product(existingProduct)
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .numberOfProducts(orderDetailDTO.getTotalOfProducts())
                .color(orderDetailDTO.getColor())
                .build();

        return orderDetailRepository.save(newOrderDetail);
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Can not find Order Detail by id = " + id));
        return OrderDetailResponse.fromOrderDetail(orderDetail);
    }




    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO)
            throws DataNotFoundException {
        // tìm xem order detail có tồn tại ko đã
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));

        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + orderDetailDTO.getProductId()));

        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getTotalOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);

        return orderDetailRepository.save(existingOrderDetail);
    }
}
