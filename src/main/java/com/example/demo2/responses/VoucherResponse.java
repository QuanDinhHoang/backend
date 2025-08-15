package com.example.demo2.responses;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherResponse {

    private Long id;
    private String name;
    private BigDecimal value;
    private LocalDate expiredDate;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
