package com.example.library.data.entity;

import com.example.library.business.rules.LibraryPolicyConstants;
import com.example.library.data.enums.BookStatus;
import com.example.library.data.enums.BorrowingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    @OneToOne(mappedBy = "borrowingRecord")
    private Fine fine;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BorrowingRecord() {
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = BorrowingStatus.PENDING;
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void approve() {
        // TODO: Support APPROVED state when the detailed workflow is enabled.
        this.status = BorrowingStatus.APPROVED;
    }

    public void reject() {
        this.status = BorrowingStatus.REJECTED;
    }

    public void markAsBorrowed() {
        this.status = BorrowingStatus.BORROWED;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(LibraryPolicyConstants.DEFAULT_BORROW_DAYS);
        if (book != null) {
            book.updateStatus(BookStatus.BORROWED);
        }
    }

    public void markAsReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.status = BorrowingStatus.RETURNED;
        if (book != null) {
            book.updateStatus(BookStatus.AVAILABLE);
        }
    }

    public boolean isOverdue() {
        LocalDate effectiveReturn = returnDate != null ? returnDate : LocalDate.now();
        return dueDate != null && effectiveReturn.isAfter(dueDate);
    }

    public long calculateOverdueDays() {
        if (!isOverdue()) {
            return 0L;
        }
        LocalDate effectiveReturn = returnDate != null ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, effectiveReturn);
    }

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public BorrowingStatus getStatus() { return status; }
    public void setStatus(BorrowingStatus status) { this.status = status; }
    public Fine getFine() { return fine; }
    public void setFine(Fine fine) { this.fine = fine; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
