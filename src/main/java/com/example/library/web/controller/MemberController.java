package com.example.library.web.controller;

import com.example.library.business.service.MemberService;
import com.example.library.web.dto.BorrowingRecordDTO;
import com.example.library.web.dto.MemberDTO;
import com.example.library.web.dto.MemberRequestDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.createMember(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, requestDTO));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateMember(@PathVariable Long id) {
        memberService.deactivateMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/borrowing-history")
    public ResponseEntity<List<BorrowingRecordDTO>> getBorrowingHistory(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getBorrowingHistory(id));
    }
}
