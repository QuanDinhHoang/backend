package com.example.demo2.services.Impl;

import com.example.demo2.dtos.OrderDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.Order;
import com.example.demo2.models.User;
import com.example.demo2.models.enums.OrderStatus;
import com.example.demo2.repositories.OrderRepository;
import com.example.demo2.repositories.UserRepository;
import com.example.demo2.services.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userReository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        //Tim userId co ton tai hay ko
        User user = userReository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Can not find the user with id " + orderDTO.getUserId()));

        //Covert OrderDTO -> Order
        //Su dung thu vien model mapper
        // Tạo một luồng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId)); // Bỏ qua ánh xạ cho trường id

        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);

        order.setUser(user);
        order.setOrderDate(new Date()); // Lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDING); // Trạng thái đơn hàng là đang chờ xử lý

        // Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }

        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOrder(Long id) throws DataNotFoundException {
        return orderRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Can not find order by id"));
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Cannot find order with id: " + id));

        User existingUser = userReository.findById(orderDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        // no hard-delete, ⇒ please soft-delete
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }


    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
