package com.example.library.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReturnBookRequestDTO {

    @NotNull
    private Long recordId;
    private LocalDate returnDate;

    public ReturnBookRequestDTO() {
    }

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
