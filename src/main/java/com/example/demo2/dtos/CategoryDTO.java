package com.example.demo2.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data //Se thuc hien toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
public class CategoryDTO {
    @NotEmpty(message = "Category's name can not be empty") //Khong cho phep truong de trong
    private String name;

}
