package com.example.demo2.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data //Se thuc hien toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
public class OrderDTO {

    @JsonProperty("user_id")
    //De biet ai da dat hang
    @Min(value = 1, message = "User Id must be > 0")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

    //email o orders ko nhat thiet phai giong voi email o bang users
    private String email;

    //phone number o orders ko nhat thiet phai giong voi phone number o bang users
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required") // phone number ko dc bo trong
    @Size(min = 5, message = "Phone number must be >= 5")
    private String phoneNumber;

    private String address;
    private String note;

    //order_date va status tu sinh ra


    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;


    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    //Tracking number va shipping date he thong su ly ko can them
    @JsonProperty("payment_method")
    private String paymentMethod;

    //active ko can them

}
