package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.VoucherDTO;
import com.example.demo2.models.Voucher;
import com.example.demo2.responses.VoucherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVoucherService {

    Voucher createVoucher(VoucherDTO voucherDTO);

    VoucherResponse getVoucherById(Long id);

    Page<VoucherResponse> getAllVouchers(Pageable pageable);

    List<VoucherResponse> getAvailableVouchers();

    Voucher updateVoucher(Long id, VoucherDTO voucherDTO);

    void deleteVoucher(Long id);

    List<VoucherResponse> searchVouchersByName(String name);
}
