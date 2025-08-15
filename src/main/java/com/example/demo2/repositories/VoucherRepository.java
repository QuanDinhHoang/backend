package com.example.demo2.repositories;

import com.example.demo2.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("SELECT v FROM Voucher v WHERE v.expiredDate >= :today AND v.quantity > 0")
    List<Voucher> findAvailableVouchers(@Param("today") LocalDate today);

    List<Voucher> findByNameContainingIgnoreCase(String name);
}
