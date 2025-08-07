package com.example.demo2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data //Se thuc hien toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
@Builder
public class ProductDTO {
    @NotBlank(message ="Title is required") //Dùng để validate rằng chuỗi không được rỗng và không chỉ toàn khoảng trắng, se thông báo lỗi trả về nếu trường name không hợp lệ (tức là rỗng hoặc chỉ toàn khoảng trắng)
    @Size(min =3, max = 200, message = "Title must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 10000000, message = "Price must be less than or equal to 10.000.000")
    private float price;
    private String thumbnail;
    private String description;

    //create_at va update_at ko can them vi no tu tao o database

    @JsonProperty("category_id") // neu ta muon su dung ten ta tu dat ma no ko giong voi thuoc tinh trong table thi phai su dung @JsonProperty, neu giong nhau thi ko su dung
    private Long categoryId;

    //Them vao de su ly them anh vao product thay cho thumbnail
    //private MultipartFile file;

    //Bay gio thi truyen vao nhieu file anh
    //private List<MultipartFile> files;
}
