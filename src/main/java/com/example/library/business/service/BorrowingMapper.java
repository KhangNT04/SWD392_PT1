package com.example.library.business.service;

import com.example.library.data.entity.BorrowingRecord;
import com.example.library.web.dto.BorrowingRecordDTO;
import java.math.BigDecimal;

final class BorrowingMapper {

    private BorrowingMapper() {
    }

    static BorrowingRecordDTO toDto(BorrowingRecord record) {
        BigDecimal fineAmount = record.getFine() != null ? record.getFine().getAmount() : null;
        return new BorrowingRecordDTO(
                record.getRecordId(),
                record.getMember() != null ? record.getMember().getMemberId() : null,
                record.getMember() != null ? record.getMember().getFullName() : null,
                record.getBook() != null ? record.getBook().getBookId() : null,
                record.getBook() != null ? record.getBook().getTitle() : null,
                record.getBorrowDate(),
                record.getDueDate(),
                record.getReturnDate(),
                record.getStatus(),
                fineAmount);
    }
}
