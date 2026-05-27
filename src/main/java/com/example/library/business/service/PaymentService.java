package com.example.library.business.service;

import com.example.library.business.exception.PaymentFailedException;
import com.example.library.business.exception.ResourceNotFoundException;
import com.example.library.data.entity.Fine;
import com.example.library.data.entity.Payment;
import com.example.library.data.enums.FineStatus;
import com.example.library.data.enums.PaymentStatus;
import com.example.library.data.repository.FineRepository;
import com.example.library.data.repository.PaymentRepository;
import com.example.library.web.dto.PaymentDTO;
import com.example.library.web.dto.PaymentRequestDTO;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final FineRepository fineRepository;

    public PaymentService(PaymentRepository paymentRepository, FineRepository fineRepository) {
        this.paymentRepository = paymentRepository;
        this.fineRepository = fineRepository;
    }

    @Transactional
    public PaymentDTO payFine(PaymentRequestDTO requestDTO) {
        Fine fine = fineRepository.findById(requestDTO.getFineId())
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + requestDTO.getFineId()));
        if (fine.getStatus() != FineStatus.UNPAID) {
            throw new PaymentFailedException("Fine is not available for payment.");
        }
        if (requestDTO.getAmount() == null || fine.getAmount() == null || fine.getAmount().compareTo(requestDTO.getAmount()) != 0) {
            throw new PaymentFailedException("Payment amount must match the fine amount.");
        }

        Payment payment = paymentRepository.findByFine_FineId(fine.getFineId()).orElseGet(Payment::new);
        payment.setFine(fine);
        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionCode(generateTransactionCode());
        payment.setPaymentDate(LocalDateTime.now());
        // TODO: Replace success placeholder with real payment gateway integration.
        Payment saved = paymentRepository.save(payment);

        fine.setStatus(FineStatus.PAID);
        fine.setPayment(saved);
        fineRepository.save(fine);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long paymentId) {
        return toDto(getPaymentEntity(paymentId));
    }

    @Transactional(readOnly = true)
    public PaymentDTO getPaymentByFineId(Long fineId) {
        return toDto(paymentRepository.findByFine_FineId(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for fine id: " + fineId)));
    }

    private Payment getPaymentEntity(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
    }

    private String generateTransactionCode() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private PaymentDTO toDto(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getFine() != null ? payment.getFine().getFineId() : null,
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getTransactionCode(),
                payment.getPaymentDate());
    }
}
