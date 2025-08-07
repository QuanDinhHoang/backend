package com.example.demo2.controllers;

import com.example.demo2.dtos.CategoryDTO;
import com.example.demo2.models.Category;
import com.example.demo2.services.Impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
@RequiredArgsConstructor // tu dong tao service ma ko can them constructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    //Neu tham so truyen vao la 1 object(JSON) thi phai su dung Data Transfer Object(hay DTO) de bien JSON thanh object truyen vao
    //@RequestBody giup doc lay noi dung trong JSON
    //@Valid de co the su dung dc @NotEmpty trong CategoryDTO
    public ResponseEntity<?> createCategory( // ResponseEntity<?> phai de la ? boi ve ket qua return co the la String hoac la List
                                             @Valid @RequestBody CategoryDTO categoryDTO,
                                             BindingResult result){ //Them moi 1 ban ghi category
        if(result.hasErrors()){
            List<String> errorMessages =result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("insert category " + categoryDTO + " successfully");
    }

    //Hien thi tat ca cac categories
    @GetMapping("") //http://localhost:8080/api/v1/categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,   //Su dung trong phan trang , phan bao nhieu trang, moi trang gioi han bao nhieu , @RequestParam("page") la tham so gui qua tu trinh duyet web
            @RequestParam("limit") int limit
    ){
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);

        //return ResponseEntity.ok(String.format("getCategories, page = %d, limit = %d", page, limit)); // ko can paging vi so luong categories lay ra it
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id,
                                                 @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update category successfully! with id = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(" delete category with id = " + id + " successfully!");
    }
}
