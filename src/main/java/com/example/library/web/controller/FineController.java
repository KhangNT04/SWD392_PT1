package com.example.library.web.controller;

import com.example.library.business.service.FineService;
import com.example.library.business.service.PaymentService;
import com.example.library.web.dto.FineDTO;
import com.example.library.web.dto.PaymentDTO;
import com.example.library.web.dto.PaymentRequestDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    private final FineService fineService;
    private final PaymentService paymentService;

    public FineController(FineService fineService, PaymentService paymentService) {
        this.fineService = fineService;
        this.paymentService = paymentService;
    }

    @GetMapping("/{fineId}")
    public ResponseEntity<FineDTO> getFineById(@PathVariable Long fineId) {
        return ResponseEntity.ok(fineService.getFineById(fineId));
    }

    @GetMapping("/unpaid")
    public ResponseEntity<List<FineDTO>> getUnpaidFines() {
        return ResponseEntity.ok(fineService.getUnpaidFines());
    }

    @PostMapping("/{recordId}/calculate")
    public ResponseEntity<FineDTO> calculateOverdueFine(@PathVariable Long recordId) {
        return ResponseEntity.ok(fineService.calculateOverdueFine(recordId));
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentDTO> payFine(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.ok(paymentService.payFine(requestDTO));
    }
}
