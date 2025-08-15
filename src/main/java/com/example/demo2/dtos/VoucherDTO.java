package com.example.demo2.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherDTO {

    @NotBlank(message = "Tên voucher không được để trống")
    private String name;

    @NotNull(message = "Giá trị voucher không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị voucher phải lớn hơn 0")
    private BigDecimal value;

    @NotNull(message = "Ngày hết hạn không được để trống")
    @Future(message = "Ngày hết hạn phải trong tương lai")
    private LocalDate expiredDate;

    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int quantity;
}
