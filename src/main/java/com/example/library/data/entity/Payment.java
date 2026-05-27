package com.example.library.data.entity;

import com.example.library.data.enums.PaymentMethod;
import com.example.library.data.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "fine_id", unique = true)
    private Fine fine;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionCode;
    private LocalDateTime paymentDate;

    public Payment() {
    }

    public boolean processPayment() {
        // TODO: Integrate with a real payment gateway.
        markSuccess();
        return true;
    }

    public void markSuccess() {
        status = PaymentStatus.SUCCESS;
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
        if (transactionCode == null || transactionCode.isBlank()) {
            transactionCode = UUID.randomUUID().toString();
        }
    }

    public void markFailed() {
        status = PaymentStatus.FAILED;
    }

    public String generateReceipt() {
        return "PAY-" + (transactionCode == null ? UUID.randomUUID() : transactionCode);
    }

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public Fine getFine() { return fine; }
    public void setFine(Fine fine) { this.fine = fine; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}
