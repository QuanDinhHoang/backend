package com.example.demo2.controllers;

import com.example.demo2.dtos.VoucherDTO;
import com.example.demo2.responses.VoucherResponse;
import com.example.demo2.services.interfaces.IVoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final IVoucherService voucherService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<VoucherResponse> vouchers = voucherService.getAllVouchers(pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("vouchers", vouchers.getContent());  // danh sách vouchers
        response.put("totalPage", vouchers.getTotalPages()); // tổng số trang
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<VoucherResponse>> getAvailableVouchers() {
        List<VoucherResponse> availableVouchers = voucherService.getAvailableVouchers();
        return ResponseEntity.ok(availableVouchers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> getVoucherById(@PathVariable Long id) {
        VoucherResponse voucher = voucherService.getVoucherById(id);
        return ResponseEntity.ok(voucher);
    }

    @PostMapping("")
    public ResponseEntity<?> createVoucher(
            @Valid @RequestBody VoucherDTO voucherDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            
            voucherService.createVoucher(voucherDTO);
            return ResponseEntity.ok("Tạo voucher thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoucher(
            @PathVariable Long id,
            @Valid @RequestBody VoucherDTO voucherDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            
            voucherService.updateVoucher(id, voucherDTO);
            return ResponseEntity.ok("Cập nhật voucher thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long id) {
        try {
            voucherService.deleteVoucher(id);
            return ResponseEntity.ok("Xóa voucher thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<VoucherResponse>> searchVouchers(@RequestParam String name) {
        List<VoucherResponse> vouchers = voucherService.searchVouchersByName(name);
        return ResponseEntity.ok(vouchers);
    }
}
