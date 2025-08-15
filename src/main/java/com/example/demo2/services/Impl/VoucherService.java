package com.example.demo2.services.Impl;

import com.example.demo2.dtos.VoucherDTO;
import com.example.demo2.models.Voucher;
import com.example.demo2.repositories.VoucherRepository;
import com.example.demo2.responses.VoucherResponse;
import com.example.demo2.services.interfaces.IVoucherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService implements IVoucherService {

    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;

    @Override
    public Voucher createVoucher(VoucherDTO voucherDTO) {
        Voucher voucher = modelMapper.map(voucherDTO, Voucher.class);
        return voucherRepository.save(voucher);
    }

    @Override
    public VoucherResponse getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher với id: " + id));
        
        return modelMapper.map(voucher, VoucherResponse.class);
    }

    @Override
    public Page<VoucherResponse> getAllVouchers(Pageable pageable) {
        Page<Voucher> voucherPage = voucherRepository.findAll(pageable);
        return voucherPage.map(voucher -> modelMapper.map(voucher, VoucherResponse.class));
    }

    @Override
    public List<VoucherResponse> getAvailableVouchers() {
        List<Voucher> availableVouchers = voucherRepository.findAvailableVouchers(LocalDate.now());
        return availableVouchers.stream()
                .map(voucher -> modelMapper.map(voucher, VoucherResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Voucher updateVoucher(Long id, VoucherDTO voucherDTO) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher với id: " + id));

        // Cập nhật thông tin voucher
        existingVoucher.setName(voucherDTO.getName());
        existingVoucher.setValue(voucherDTO.getValue());
        existingVoucher.setExpiredDate(voucherDTO.getExpiredDate());
        existingVoucher.setQuantity(voucherDTO.getQuantity());

        return voucherRepository.save(existingVoucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher với id: " + id));
        
        voucherRepository.delete(voucher);
    }

    @Override
    public List<VoucherResponse> searchVouchersByName(String name) {
        List<Voucher> vouchers = voucherRepository.findByNameContainingIgnoreCase(name);
        return vouchers.stream()
                .map(voucher -> modelMapper.map(voucher, VoucherResponse.class))
                .collect(Collectors.toList());
    }
}
