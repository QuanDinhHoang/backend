package com.example.demo2.repositories;

import com.example.demo2.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository //co hay ko co cai nay deu duoc boi vi ta da khai bao JpaRepository la Java Spring da hieu day la su ly repository
//User save(User entity);
//Optional<User> findById(Long id);
//List<User> findAll();
//void deleteById(Long id);
//boolean existsById(Long id);boolean existsById(Long id);
//long count();
public interface CategoryReposity extends JpaRepository<Category, Long> {


}
