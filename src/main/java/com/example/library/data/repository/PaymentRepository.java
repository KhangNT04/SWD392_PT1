package com.example.library.data.repository;

import com.example.library.data.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionCode(String transactionCode);
    Optional<Payment> findByFine_FineId(Long fineId);
}
