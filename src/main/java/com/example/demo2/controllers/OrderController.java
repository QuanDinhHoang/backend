package com.example.demo2.controllers;
import com.example.demo2.dtos.OrderDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.Order;
import com.example.demo2.services.interfaces.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
                                         BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages =result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages); // it will return list
            }
            Order orderResponse = orderService.createOrder(orderDTO);

            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}") //Lay danh sach orders cua 1 user
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok().body(orders);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}") //Lay chi tiet order cua orderId
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId){
        try {
            Order existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok().body(existingOrder);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT http://localhost:8088/api/v1/orders/2
    //công việc của admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) throws DataNotFoundException {
        Order order = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
        // xóa mềm ⇒ cập nhật trường active = false
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body("Order deleted successfully.");
    }

}
