package com.example.library.web.dto;

import com.example.library.data.enums.FineStatus;
import java.math.BigDecimal;

public class FineDTO {

    private Long fineId;
    private Long borrowingRecordId;
    private BigDecimal amount;
    private String reason;
    private FineStatus status;

    public FineDTO() {
    }

    public FineDTO(Long fineId, Long borrowingRecordId, BigDecimal amount, String reason, FineStatus status) {
        this.fineId = fineId;
        this.borrowingRecordId = borrowingRecordId;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
    }

    public Long getFineId() { return fineId; }
    public void setFineId(Long fineId) { this.fineId = fineId; }
    public Long getBorrowingRecordId() { return borrowingRecordId; }
    public void setBorrowingRecordId(Long borrowingRecordId) { this.borrowingRecordId = borrowingRecordId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public FineStatus getStatus() { return status; }
    public void setStatus(FineStatus status) { this.status = status; }
}
