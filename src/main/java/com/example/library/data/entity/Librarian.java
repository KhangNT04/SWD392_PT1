package com.example.library.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "librarians")
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long librarianId;
    private String fullName;
    private String phone;
    private String employeeCode;
    private LocalDate hireDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private UserAccount userAccount;

    public Librarian() {
    }

    public void addBook(Book book) {
        // TODO: Delegate to BookService.
    }

    public void updateBook(Book book) {
        // TODO: Delegate to BookService.
    }

    public void deleteBook(Long bookId) {
        // TODO: Delegate to BookService.
    }

    public void manageMember(Member member) {
        // TODO: Delegate to MemberService.
    }

    public void approveBorrowingRequest(BorrowingRecord record) {
        // TODO: Delegate to BorrowingService.
    }

    public void receiveReturnedBook(BorrowingRecord record) {
        // TODO: Delegate to BorrowingService.
    }

    public List<BorrowingRecord> checkOverdueBooks() {
        // TODO: Delegate to BorrowingService.
        return List.of();
    }

    public Long getLibrarianId() { return librarianId; }
    public void setLibrarianId(Long librarianId) { this.librarianId = librarianId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public UserAccount getUserAccount() { return userAccount; }
    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }
}
