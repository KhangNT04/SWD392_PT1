package com.example.library.data.entity;

import com.example.library.data.enums.FineStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fines")
public class Fine {

    private static final BigDecimal DEFAULT_DAILY_FINE_RATE = BigDecimal.valueOf(5000L);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    @OneToOne
    @JoinColumn(name = "record_id", unique = true)
    private BorrowingRecord borrowingRecord;

    private BigDecimal amount;
    private String reason;

    @Enumerated(EnumType.STRING)
    private FineStatus status;

    @OneToOne(mappedBy = "fine")
    private Payment payment;

    private LocalDateTime createdAt;

    public Fine() {
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = FineStatus.UNPAID;
        }
    }

    public BigDecimal calculateFine(long overdueDays) {
        // TODO: Replace placeholder calculation with configurable business rules.
        return BigDecimal.valueOf(overdueDays).multiply(DEFAULT_DAILY_FINE_RATE);
    }

    public void markAsPaid() {
        this.status = FineStatus.PAID;
    }

    public boolean isPaid() {
        return status == FineStatus.PAID;
    }

    public Long getFineId() { return fineId; }
    public void setFineId(Long fineId) { this.fineId = fineId; }
    public BorrowingRecord getBorrowingRecord() { return borrowingRecord; }
    public void setBorrowingRecord(BorrowingRecord borrowingRecord) { this.borrowingRecord = borrowingRecord; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public FineStatus getStatus() { return status; }
    public void setStatus(FineStatus status) { this.status = status; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
