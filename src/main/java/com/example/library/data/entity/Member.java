package com.example.library.data.entity;

import com.example.library.data.enums.AccountStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String fullName;
    private String phone;
    private String address;
    private LocalDate membershipDate;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "member")
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    public Member() {
    }

    public List<Book> searchBooks(String keyword) {
        // TODO: Delegate to BookService/BookRepository.
        return List.of();
    }

    public BorrowingRecord borrowBook(Book book) {
        // TODO: Delegate borrowing logic to BorrowingService.
        BorrowingRecord record = new BorrowingRecord();
        record.setMember(this);
        record.setBook(book);
        return record;
    }

    public void returnBook(Book book) {
        // TODO: Delegate return handling to BorrowingService.
    }

    public List<BorrowingRecord> viewBorrowingHistory() {
        return borrowingRecords;
    }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public void setMembershipDate(LocalDate membershipDate) { this.membershipDate = membershipDate; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public UserAccount getUserAccount() { return userAccount; }
    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }
    public List<BorrowingRecord> getBorrowingRecords() { return borrowingRecords; }
    public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) { this.borrowingRecords = borrowingRecords; }
}
