package com.example.library.web.dto;

import jakarta.validation.constraints.NotNull;

public class BorrowRequestDTO {

    @NotNull
    private Long memberId;

    @NotNull
    private Long bookId;

    public BorrowRequestDTO() {
    }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
}
