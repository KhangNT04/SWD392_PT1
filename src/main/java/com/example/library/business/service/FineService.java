package com.example.library.business.service;

import com.example.library.business.exception.OverdueFineException;
import com.example.library.business.exception.ResourceNotFoundException;
import com.example.library.business.rules.FineCalculationRule;
import com.example.library.data.entity.BorrowingRecord;
import com.example.library.data.entity.Fine;
import com.example.library.data.enums.FineStatus;
import com.example.library.data.repository.BorrowingRepository;
import com.example.library.data.repository.FineRepository;
import com.example.library.web.dto.FineDTO;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FineService {

    private final FineRepository fineRepository;
    private final BorrowingRepository borrowingRepository;
    private final FineCalculationRule fineCalculationRule;

    public FineService(FineRepository fineRepository, BorrowingRepository borrowingRepository,
            FineCalculationRule fineCalculationRule) {
        this.fineRepository = fineRepository;
        this.borrowingRepository = borrowingRepository;
        this.fineCalculationRule = fineCalculationRule;
    }

    @Transactional
    public FineDTO calculateOverdueFine(Long borrowingRecordId) {
        BorrowingRecord record = borrowingRepository.findById(borrowingRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + borrowingRecordId));
        long overdueDays = record.calculateOverdueDays();
        if (overdueDays <= 0) {
            throw new OverdueFineException("Borrowing record is not overdue.");
        }

        Fine fine = fineRepository.findByBorrowingRecord_RecordId(borrowingRecordId).orElseGet(Fine::new);
        fine.setBorrowingRecord(record);
        fine.setAmount(calculateAmount(overdueDays));
        fine.setReason("Overdue return for " + overdueDays + " day(s)");
        fine.setStatus(FineStatus.UNPAID);
        Fine saved = fineRepository.save(fine);
        record.setFine(saved);
        borrowingRepository.save(record);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public FineDTO getFineById(Long fineId) {
        return toDto(getFineEntity(fineId));
    }

    @Transactional(readOnly = true)
    public List<FineDTO> getUnpaidFines() {
        return fineRepository.findByStatus(FineStatus.UNPAID).stream().map(this::toDto).toList();
    }

    @Transactional
    public FineDTO markFineAsPaid(Long fineId) {
        Fine fine = getFineEntity(fineId);
        fine.markAsPaid();
        return toDto(fineRepository.save(fine));
    }

    private Fine getFineEntity(Long fineId) {
        return fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + fineId));
    }

    private BigDecimal calculateAmount(long overdueDays) {
        BigDecimal amount = fineCalculationRule.calculate(overdueDays);
        if (amount == null || amount.signum() < 0) {
            throw new OverdueFineException("Fine calculation returned an invalid amount.");
        }
        return amount;
    }

    private FineDTO toDto(Fine fine) {
        return new FineDTO(
                fine.getFineId(),
                fine.getBorrowingRecord() != null ? fine.getBorrowingRecord().getRecordId() : null,
                fine.getAmount(),
                fine.getReason(),
                fine.getStatus());
    }
}
