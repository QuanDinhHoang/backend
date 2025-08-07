package com.example.demo2.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass //@MappedSuperclass in Java (specifically JPA/Hibernate) is used to define a base class whose fields are inherited by other entity classes, but which itself is not an entity and not mapped to a database table.
public class BaseEntity {

    @Column(name = "created_at") // Luc nay la tao ban ghi thi se tao createdAt de bo vo bang products
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // Luc nay la tao ban ghi thi se tao createdAt de bo vo bang products
    private LocalDateTime updatedAt;

    //2 ham sau tu dong them gia tri now() cho createdAt va updatedAt
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
