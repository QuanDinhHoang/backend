package com.example.demo2.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Data //Se thuc hien toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //Khoi tao ko co tham so
//@Builder
@MappedSuperclass
public class BaseResponse {
    @JsonProperty("created_at")
    private LocalDateTime createAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;


}
