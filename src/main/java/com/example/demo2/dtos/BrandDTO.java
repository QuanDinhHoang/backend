package com.example.demo2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDTO {

    @Size(max = 500, message = "Brand image URL cannot exceed 500 characters")
    private String brandImg;

    private boolean active;

    @NotBlank(message = "Brand name cannot be empty")
    @Size(max = 100, message = "Brand name cannot exceed 100 characters")
    private String name;
}

