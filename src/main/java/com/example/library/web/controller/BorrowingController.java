package com.example.library.web.controller;

import com.example.library.business.service.BorrowingService;
import com.example.library.web.dto.BorrowRequestDTO;
import com.example.library.web.dto.BorrowingRecordDTO;
import com.example.library.web.dto.ReturnBookRequestDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/requests")
    public ResponseEntity<BorrowingRecordDTO> createBorrowRequest(@Valid @RequestBody BorrowRequestDTO requestDTO) {
        return ResponseEntity.ok(borrowingService.createBorrowRequest(requestDTO));
    }

    @PatchMapping("/{recordId}/approve")
    public ResponseEntity<BorrowingRecordDTO> approveBorrowingRequest(@PathVariable Long recordId) {
        return ResponseEntity.ok(borrowingService.approveBorrowingRequest(recordId));
    }

    @PatchMapping("/{recordId}/reject")
    public ResponseEntity<BorrowingRecordDTO> rejectBorrowingRequest(@PathVariable Long recordId) {
        return ResponseEntity.ok(borrowingService.rejectBorrowingRequest(recordId));
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowingRecordDTO> returnBook(@Valid @RequestBody ReturnBookRequestDTO requestDTO) {
        return ResponseEntity.ok(borrowingService.returnBook(requestDTO));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<BorrowingRecordDTO>> getPendingRequests() {
        return ResponseEntity.ok(borrowingService.getPendingRequests());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowingRecordDTO>> getOverdueRecords() {
        return ResponseEntity.ok(borrowingService.getOverdueRecords());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowingRecordDTO>> getBorrowingHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowingService.getBorrowingHistory(memberId));
    }
}
