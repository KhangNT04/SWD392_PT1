# Library Management System - DESIGN.md

## 1. Project Overview

### Business Scenario

The **Library Management System** is a Java Spring Boot backend application for managing library operations.

The system allows:

- Members to log in, search books, borrow books, return books, view borrowing history, and pay overdue fines.
- Librarians to manage books, manage members, approve borrowing requests, reject borrowing requests, receive returned books, and check overdue books.
- System Administrators to manage user accounts and basic system settings.
- The Payment System to process overdue fine payments.

The system tracks:

- Book information
- Book categories
- Book availability
- Borrowing requests
- Borrowing records
- Borrow dates
- Due dates
- Return dates
- Overdue fines
- Fine payments

### Purpose

The purpose of this project is to create a clear Java Spring Boot source code template for a Library Management System.

This document will be used as input context for **GitHub Copilot** to generate Java Spring Boot template code.

The generated code should focus on:

- Clean package structure
- Entity design
- Repository layer
- DTO layer
- Service layer
- Controller layer
- Exception handling
- Borrow book flow
- Return book flow
- Fine calculation flow
- Fine payment flow

Full authentication and production security are not required.

---

## 2. AI Tools Used

### ChatGPT

ChatGPT is used for:

- Requirement analysis
- Use case identification
- UML design planning
- Class design
- Sequence flow design
- Package structure design
- Java Spring Boot architecture suggestion
- Source code template specification

### PlantUML

PlantUML is used for generating UML diagrams:

- Use Case Diagram
- Class Diagram
- Sequence Diagram
- Package Diagram

### GitHub Copilot

GitHub Copilot will be used later to generate Java Spring Boot source code templates from this `DESIGN.md` file.

Copilot should generate template code only and add TODO comments where detailed implementation is needed.

---

## 3. Functional Requirements

| ID | Requirement |
|---|---|
| FR01 | Members can log in. |
| FR02 | Members can search books by title, author, or category. |
| FR03 | Members can view book information. |
| FR04 | Members can borrow available books. |
| FR05 | Members can return borrowed books. |
| FR06 | Members can view borrowing history. |
| FR07 | Members can pay overdue fines. |
| FR08 | Librarians can add books. |
| FR09 | Librarians can update books. |
| FR10 | Librarians can delete books. |
| FR11 | Librarians can manage member accounts. |
| FR12 | Librarians can approve borrowing requests. |
| FR13 | Librarians can reject borrowing requests. |
| FR14 | Librarians can receive returned books. |
| FR15 | Librarians can check overdue books. |
| FR16 | The system checks book availability before borrowing. |
| FR17 | The system creates borrowing records. |
| FR18 | The system updates borrowing record status. |
| FR19 | The system updates book status. |
| FR20 | The system calculates overdue fines when books are returned late. |
| FR21 | The system records fine payment information. |
| FR22 | System Administrators can manage user accounts. |
| FR23 | System Administrators can manage basic system settings. |

---

## 4. Non-Functional Requirements

| ID | Requirement |
|---|---|
| NFR01 | The system should use Java Spring Boot. |
| NFR02 | The system should use RESTful API design. |
| NFR03 | The system should use Jakarta Persistence. |
| NFR04 | The system should use Spring Data JPA. |
| NFR05 | The system should use constructor injection. |
| NFR06 | The system should use DTOs for request and response data. |
| NFR07 | The system should keep controllers thin. |
| NFR08 | The system should keep business logic inside service classes. |
| NFR09 | The system should keep database access inside repository interfaces. |
| NFR10 | The system should use custom exceptions. |
| NFR11 | The system should use a global exception handler. |
| NFR12 | The system should be easy to maintain and extend. |
| NFR13 | The system should use `BigDecimal` for money values. |
| NFR14 | The system should use `LocalDate` and `LocalDateTime` for date and time values. |
| NFR15 | The system should not expose JPA entities directly from controllers. |
| NFR16 | The system should include TODO comments for unfinished detailed logic. |
| NFR17 | The system should not implement full authentication/security in the template. |

---

## 5. Actors

| Actor | Description | Responsibilities |
|---|---|---|
| Member | A library user who borrows books. | Log in, search books, borrow books, return books, view borrowing history, pay fines. |
| Librarian | A staff member who manages library operations. | Manage books, manage members, approve borrowing requests, receive returned books, check overdue books. |
| System Administrator | A user who manages system-level data. | Manage user accounts and basic system settings. |
| Payment System | External or internal payment processor. | Process fine payment and return payment result. |

---

## 6. Use Cases

| Use Case | Actor | Description |
|---|---|---|
| Login | Member, Librarian, System Administrator | Allows users to access the system. |
| Search Books | Member | Searches books by title, author, or category. |
| Borrow Book | Member | Creates a borrow request for an available book. |
| Return Book | Member | Returns a borrowed book. |
| View Borrowing History | Member | Displays all borrowing records of a member. |
| Pay Fine | Member | Pays an unpaid overdue fine. |
| Manage Books | Librarian | General book management use case. |
| Add Book | Librarian | Adds a new book to the system. |
| Update Book | Librarian | Updates existing book information. |
| Delete Book | Librarian | Deletes or removes a book. |
| Manage Members | Librarian | General member account management. |
| Create Member Account | Librarian | Creates a new member account. |
| Update Member Account | Librarian | Updates member information. |
| Deactivate Member Account | Librarian | Deactivates a member account. |
| Approve Borrowing Request | Librarian | Approves a pending borrowing request. |
| Reject Borrowing Request | Librarian | Rejects an invalid borrowing request. |
| Receive Returned Book | Librarian | Confirms that a borrowed book was returned. |
| Check Overdue Books | Librarian | Lists borrowing records that are overdue. |
| Check Book Availability | System | Checks whether a book is available. |
| Create Borrowing Record | System | Creates a borrowing record. |
| Update Book Status | System | Updates book status after borrowing or returning. |
| Calculate Overdue Fine | System | Calculates fine for late return. |
| Process Fine Payment | Payment System | Processes fine payment. |
| Manage User Accounts | System Administrator | Manages user login accounts. |
| Manage System Settings | System Administrator | Manages basic system settings. |

---

## 7. Include and Extend Relationships

### Include Relationships

`<<include>>` means the included use case is always part of the main use case.

| Main Use Case | Included Use Case | Explanation |
|---|---|---|
| Borrow Book | Login | A member should be authenticated before borrowing. |
| Borrow Book | Search Books | A member searches and selects a book before borrowing. |
| Borrow Book | Check Book Availability | The system must check availability before borrowing. |
| Approve Borrowing Request | Create Borrowing Record | A borrowing record must exist for approval. |
| Approve Borrowing Request | Update Book Status | Book status must become `BORROWED` after approval. |
| Return Book | Login | A member should be authenticated before returning. |
| Return Book | Receive Returned Book | A librarian confirms the returned book. |
| Return Book | Update Book Status | Book status must become `AVAILABLE` after return. |
| Check Overdue Books | Calculate Overdue Fine | Overdue checking may require fine calculation. |
| Pay Fine | Process Fine Payment | Paying a fine requires payment processing. |
| Manage Books | Login | Librarian must be authenticated. |
| Manage Members | Login | Librarian must be authenticated. |
| Manage User Accounts | Login | Administrator must be authenticated. |
| Manage System Settings | Login | Administrator must be authenticated. |

### Extend Relationships

`<<extend>>` means the extending use case happens only under a condition.

| Base Use Case | Extended Use Case | Condition |
|---|---|---|
| Borrow Book | Approve Borrowing Request | If the request is valid and the book is available. |
| Borrow Book | Reject Borrowing Request | If the book is unavailable or the request is invalid. |
| Return Book | Calculate Overdue Fine | If the book is returned after the due date. |
| Return Book | Pay Fine | If an overdue fine exists. |
| Manage Books | Add Book | Librarian chooses to add a book. |
| Manage Books | Update Book | Librarian chooses to update a book. |
| Manage Books | Delete Book | Librarian chooses to delete a book. |
| Manage Members | Create Member Account | Librarian creates a new member. |
| Manage Members | Update Member Account | Librarian updates member data. |
| Manage Members | Deactivate Member Account | Librarian deactivates a member account. |
| Check Overdue Books | Send Overdue Notification | Optional future extension if overdue books are found. |

---

## 8. Main Business Flows

## 8.1 Borrow Book Flow

### Main Flow

| Step | Actor/System | Action |
|---|---|---|
| 1 | Member | Enters a search keyword. |
| 2 | System | Searches books by title, author, or category. |
| 3 | System | Displays matching books. |
| 4 | Member | Selects a book and sends a borrow request. |
| 5 | System | Checks whether the member exists. |
| 6 | System | Checks whether the book exists. |
| 7 | System | Checks whether the book status is `AVAILABLE`. |
| 8 | System | Checks borrowing policy. |
| 9 | System | Creates a borrowing record with status `PENDING`. |
| 10 | Librarian | Reviews the pending request. |
| 11 | Librarian | Approves the request. |
| 12 | System | Updates borrowing record status to `BORROWED`. |
| 13 | System | Sets borrow date and due date. |
| 14 | System | Updates book status to `BORROWED`. |
| 15 | System | Shows confirmation to the member. |

### Alternative Flow: Book Not Available

| Step | Actor/System | Action |
|---|---|---|
| 1 | Member | Sends a borrow request. |
| 2 | System | Checks book availability. |
| 3 | System | Detects that book is not available. |
| 4 | System | Throws `BookUnavailableException`. |
| 5 | System | Returns unavailable message. |

### Pseudo Logic

```java
public BorrowingRecordDTO createBorrowRequest(BorrowRequestDTO requestDTO) {
    // TODO: Find member by memberId.
    // TODO: Throw ResourceNotFoundException if member does not exist.
    // TODO: Find book by bookId.
    // TODO: Throw ResourceNotFoundException if book does not exist.
    // TODO: Check book status is AVAILABLE.
    // TODO: Throw BookUnavailableException if book is not available.
    // TODO: Check borrowing policy.
    // TODO: Create BorrowingRecord with status PENDING.
    // TODO: Save borrowing record.
    // TODO: Convert entity to DTO.
    // TODO: Return DTO.
}
```

```java
public BorrowingRecordDTO approveBorrowingRequest(Long recordId) {
    // TODO: Find borrowing record by recordId.
    // TODO: Throw ResourceNotFoundException if record does not exist.
    // TODO: Check record status is PENDING.
    // TODO: Check book status is still AVAILABLE.
    // TODO: Set borrowing status to BORROWED.
    // TODO: Set borrowDate to LocalDate.now().
    // TODO: Set dueDate to LocalDate.now().plusDays(14).
    // TODO: Set book status to BORROWED.
    // TODO: Save book and borrowing record.
    // TODO: Convert entity to DTO.
    // TODO: Return DTO.
}
```

---

## 8.2 Return Book Flow

### Main Flow

| Step | Actor/System | Action |
|---|---|---|
| 1 | Member | Returns a borrowed book. |
| 2 | Librarian | Receives the returned book. |
| 3 | System | Loads borrowing record. |
| 4 | System | Checks that borrowing record status is `BORROWED`. |
| 5 | System | Sets return date. |
| 6 | System | Updates borrowing record status to `RETURNED`. |
| 7 | System | Updates book status to `AVAILABLE`. |
| 8 | System | Checks if return date is after due date. |
| 9 | System | If late, calculates overdue fine. |
| 10 | System | Returns result. |

### Pseudo Logic

```java
public BorrowingRecordDTO returnBook(ReturnBookRequestDTO requestDTO) {
    // TODO: Find borrowing record by recordId.
    // TODO: Throw ResourceNotFoundException if record does not exist.
    // TODO: Check record status is BORROWED.
    // TODO: Throw InvalidBorrowingRequestException if status is invalid.
    // TODO: Determine return date. If null, use LocalDate.now().
    // TODO: Set returnDate.
    // TODO: Set borrowing status to RETURNED.
    // TODO: Set book status to AVAILABLE.
    // TODO: If returnDate is after dueDate, call fineService.calculateOverdueFine(recordId).
    // TODO: Save book and borrowing record.
    // TODO: Convert entity to DTO.
    // TODO: Return DTO.
}
```

---

## 8.3 Pay Fine Flow

### Main Flow

| Step | Actor/System | Action |
|---|---|---|
| 1 | Member | Views unpaid fine. |
| 2 | Member | Sends payment request. |
| 3 | System | Loads fine by fine ID. |
| 4 | System | Checks fine status is `UNPAID`. |
| 5 | System | Validates payment amount. |
| 6 | Payment System | Processes payment. |
| 7 | System | Creates payment record. |
| 8 | System | Sets payment status to `SUCCESS`. |
| 9 | System | Updates fine status to `PAID`. |
| 10 | System | Returns payment confirmation. |

### Pseudo Logic

```java
public PaymentDTO payFine(PaymentRequestDTO requestDTO) {
    // TODO: Find fine by fineId.
    // TODO: Throw ResourceNotFoundException if fine does not exist.
    // TODO: Check fine status is UNPAID.
    // TODO: Throw PaymentFailedException if fine is already paid.
    // TODO: Validate payment amount equals fine amount.
    // TODO: Create Payment entity.
    // TODO: Set payment status to SUCCESS for simple template.
    // TODO: Generate transaction code.
    // TODO: Set payment date.
    // TODO: Update fine status to PAID.
    // TODO: Save fine and payment.
    // TODO: Convert payment entity to DTO.
    // TODO: Return DTO.
}
```

---

## 9. Class Design

## 9.1 UserAccount

### Responsibility

Stores login account information for members, librarians, and administrators.

### Fields

| Field | Type |
|---|---|
| accountId | Long |
| username | String |
| passwordHash | String |
| email | String |
| role | UserRole |
| status | AccountStatus |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

### Main Methods

```java
boolean login(String username, String password);
void logout();
void changePassword(String oldPassword, String newPassword);
boolean isActive();
```

---

## 9.2 Member

### Responsibility

Represents a library member.

### Fields

| Field | Type |
|---|---|
| memberId | Long |
| fullName | String |
| phone | String |
| address | String |
| membershipDate | LocalDate |
| status | AccountStatus |
| userAccount | UserAccount |
| borrowingRecords | List<BorrowingRecord> |

### Main Methods

```java
List<Book> searchBooks(String keyword);
BorrowingRecord borrowBook(Book book);
void returnBook(Book book);
List<BorrowingRecord> viewBorrowingHistory();
```

---

## 9.3 Librarian

### Responsibility

Represents a librarian who manages books, members, and borrowing requests.

### Fields

| Field | Type |
|---|---|
| librarianId | Long |
| fullName | String |
| phone | String |
| employeeCode | String |
| hireDate | LocalDate |
| userAccount | UserAccount |

### Main Methods

```java
void addBook(Book book);
void updateBook(Book book);
void deleteBook(Long bookId);
void manageMember(Member member);
void approveBorrowingRequest(BorrowingRecord record);
void receiveReturnedBook(BorrowingRecord record);
List<BorrowingRecord> checkOverdueBooks();
```

---

## 9.4 Book

### Responsibility

Represents a book in the library.

### Fields

| Field | Type |
|---|---|
| bookId | Long |
| title | String |
| author | String |
| isbn | String |
| publisher | String |
| publicationYear | Integer |
| status | BookStatus |
| category | Category |
| borrowingRecords | List<BorrowingRecord> |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

### Main Methods

```java
boolean isAvailable();
void updateStatus(BookStatus status);
String getBookInfo();
```

---

## 9.5 Category

### Responsibility

Represents a book category.

### Fields

| Field | Type |
|---|---|
| categoryId | Long |
| categoryName | String |
| description | String |
| books | List<Book> |

### Main Methods

```java
void addBook(Book book);
void removeBook(Book book);
List<Book> getBooks();
```

---

## 9.6 BorrowingRecord

### Responsibility

Represents a borrowing transaction.

### Fields

| Field | Type |
|---|---|
| recordId | Long |
| member | Member |
| book | Book |
| borrowDate | LocalDate |
| dueDate | LocalDate |
| returnDate | LocalDate |
| status | BorrowingStatus |
| fine | Fine |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

### Main Methods

```java
void approve();
void reject();
void markAsBorrowed();
void markAsReturned(LocalDate returnDate);
boolean isOverdue();
long calculateOverdueDays();
```

---

## 9.7 Fine

### Responsibility

Represents an overdue fine.

### Fields

| Field | Type |
|---|---|
| fineId | Long |
| borrowingRecord | BorrowingRecord |
| amount | BigDecimal |
| reason | String |
| status | FineStatus |
| payment | Payment |
| createdAt | LocalDateTime |

### Main Methods

```java
BigDecimal calculateFine(long overdueDays);
void markAsPaid();
boolean isPaid();
```

---

## 9.8 Payment

### Responsibility

Represents a payment for an overdue fine.

### Fields

| Field | Type |
|---|---|
| paymentId | Long |
| fine | Fine |
| amount | BigDecimal |
| paymentMethod | PaymentMethod |
| status | PaymentStatus |
| transactionCode | String |
| paymentDate | LocalDateTime |

### Main Methods

```java
boolean processPayment();
void markSuccess();
void markFailed();
String generateReceipt();
```

---

## 10. Enum Design

## 10.1 BookStatus

```java
public enum BookStatus {
    AVAILABLE,
    BORROWED,
    LOST,
    DAMAGED
}
```

## 10.2 BorrowingStatus

```java
public enum BorrowingStatus {
    PENDING,
    APPROVED,
    BORROWED,
    RETURNED,
    OVERDUE,
    REJECTED
}
```

Recommended simple flow:

```text
PENDING -> BORROWED -> RETURNED
```

Optional detailed flow:

```text
PENDING -> APPROVED -> BORROWED -> RETURNED
```

## 10.3 FineStatus

```java
public enum FineStatus {
    UNPAID,
    PAID,
    CANCELLED
}
```

## 10.4 PaymentStatus

```java
public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED
}
```

## 10.5 UserRole

```java
public enum UserRole {
    MEMBER,
    LIBRARIAN,
    ADMIN
}
```

## 10.6 Additional Enums

```java
public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    LOCKED
}
```

```java
public enum PaymentMethod {
    CASH,
    BANK_TRANSFER,
    CARD,
    E_WALLET
}
```

---

## 11. Relationships Between Classes

| Relationship | Multiplicity | Description |
|---|---|---|
| UserAccount - Member | 1 to 0..1 | A user account may be linked to one member. |
| UserAccount - Librarian | 1 to 0..1 | A user account may be linked to one librarian. |
| Member - BorrowingRecord | 1 to 0..* | A member can have many borrowing records. |
| Book - BorrowingRecord | 1 to 0..* | A book can appear in many borrowing records over time. |
| Category - Book | 1 to 0..* | A category can contain many books. |
| Book - Category | Many to 1 | A book belongs to one category. |
| BorrowingRecord - Fine | 1 to 0..1 | A borrowing record may generate one fine. |
| Fine - Payment | 1 to 0..1 | A fine may have one payment. |

### Recommended JPA Mapping

| Relationship | JPA Mapping |
|---|---|
| UserAccount - Member | `@OneToOne` |
| UserAccount - Librarian | `@OneToOne` |
| Member - BorrowingRecord | `@OneToMany` and `@ManyToOne` |
| Book - BorrowingRecord | `@OneToMany` and `@ManyToOne` |
| Category - Book | `@OneToMany` and `@ManyToOne` |
| BorrowingRecord - Fine | `@OneToOne` |
| Fine - Payment | `@OneToOne` |

---

## 12. Package Structure

Root package:

```text
com.example.library
```

Structure:

```text
com.example.library
│
├── LibraryManagementApplication.java
│
├── web
│   ├── controller
│   │   ├── AuthController.java
│   │   ├── BookController.java
│   │   ├── MemberController.java
│   │   ├── BorrowingController.java
│   │   └── FineController.java
│   ├── dto
│   │   ├── BookDTO.java
│   │   ├── BookRequestDTO.java
│   │   ├── MemberDTO.java
│   │   ├── MemberRequestDTO.java
│   │   ├── BorrowRequestDTO.java
│   │   ├── BorrowingRecordDTO.java
│   │   ├── ReturnBookRequestDTO.java
│   │   ├── FineDTO.java
│   │   ├── PaymentDTO.java
│   │   ├── PaymentRequestDTO.java
│   │   └── ErrorResponseDTO.java
│   └── exception_handler
│       └── GlobalExceptionHandler.java
│
├── business
│   ├── service
│   │   ├── BookService.java
│   │   ├── MemberService.java
│   │   ├── BorrowingService.java
│   │   ├── FineService.java
│   │   └── PaymentService.java
│   ├── rules
│   │   ├── BorrowingPolicy.java
│   │   └── FineCalculationRule.java
│   └── exception
│       ├── ResourceNotFoundException.java
│       ├── BookUnavailableException.java
│       ├── InvalidBorrowingRequestException.java
│       ├── OverdueFineException.java
│       └── PaymentFailedException.java
│
├── data
│   ├── repository
│   │   ├── UserAccountRepository.java
│   │   ├── MemberRepository.java
│   │   ├── LibrarianRepository.java
│   │   ├── BookRepository.java
│   │   ├── CategoryRepository.java
│   │   ├── BorrowingRepository.java
│   │   ├── FineRepository.java
│   │   └── PaymentRepository.java
│   ├── entity
│   │   ├── UserAccount.java
│   │   ├── Member.java
│   │   ├── Librarian.java
│   │   ├── Book.java
│   │   ├── Category.java
│   │   ├── BorrowingRecord.java
│   │   ├── Fine.java
│   │   └── Payment.java
│   └── enums
│       ├── UserRole.java
│       ├── AccountStatus.java
│       ├── BookStatus.java
│       ├── BorrowingStatus.java
│       ├── FineStatus.java
│       ├── PaymentStatus.java
│       └── PaymentMethod.java
│
└── config
    ├── AppConfig.java
    ├── SecurityConfig.java
    └── DatabaseConfig.java
```

---

## 13. Repository Layer

Package:

```text
com.example.library.data.repository
```

All repositories should extend:

```java
JpaRepository<EntityName, Long>
```

### Repository Interfaces and Useful Query Methods

#### BookRepository

```java
List<Book> findByTitleContainingIgnoreCase(String title);
List<Book> findByAuthorContainingIgnoreCase(String author);
List<Book> findByCategory_CategoryNameContainingIgnoreCase(String categoryName);
List<Book> findByStatus(BookStatus status);
List<Book> searchBooks(String keyword);
```

#### MemberRepository

```java
Optional<Member> findByUserAccount_Username(String username);
Optional<Member> findByUserAccount_Email(String email);
List<Member> findByStatus(AccountStatus status);
```

#### LibrarianRepository

```java
Optional<Librarian> findByEmployeeCode(String employeeCode);
Optional<Librarian> findByUserAccount_Username(String username);
```

#### CategoryRepository

```java
Optional<Category> findByCategoryNameIgnoreCase(String categoryName);
```

#### BorrowingRepository

```java
List<BorrowingRecord> findByMember_MemberId(Long memberId);
List<BorrowingRecord> findByBook_BookId(Long bookId);
List<BorrowingRecord> findByStatus(BorrowingStatus status);
List<BorrowingRecord> findByMember_MemberIdAndStatus(Long memberId, BorrowingStatus status);
List<BorrowingRecord> findByDueDateBeforeAndStatus(LocalDate date, BorrowingStatus status);
boolean existsByBook_BookIdAndStatusIn(Long bookId, List<BorrowingStatus> statuses);
```

#### FineRepository

```java
Optional<Fine> findByBorrowingRecord_RecordId(Long recordId);
List<Fine> findByStatus(FineStatus status);
```

#### PaymentRepository

```java
Optional<Payment> findByTransactionCode(String transactionCode);
Optional<Payment> findByFine_FineId(Long fineId);
```

#### UserAccountRepository

```java
Optional<UserAccount> findByUsername(String username);
Optional<UserAccount> findByEmail(String email);
boolean existsByUsername(String username);
boolean existsByEmail(String email);
```

---

## 14. Service Layer

Package:

```text
com.example.library.business.service
```

All services should use constructor injection.

### BookService

```java
List<BookDTO> searchBooks(String keyword);
BookDTO getBookById(Long bookId);
BookDTO createBook(BookRequestDTO requestDTO);
BookDTO updateBook(Long bookId, BookRequestDTO requestDTO);
void deleteBook(Long bookId);
boolean isBookAvailable(Long bookId);
void updateBookStatus(Long bookId, BookStatus status);
```

### MemberService

```java
MemberDTO getMemberById(Long memberId);
List<MemberDTO> getAllMembers();
MemberDTO createMember(MemberRequestDTO requestDTO);
MemberDTO updateMember(Long memberId, MemberRequestDTO requestDTO);
void deactivateMember(Long memberId);
List<BorrowingRecordDTO> getBorrowingHistory(Long memberId);
```

### BorrowingService

```java
BorrowingRecordDTO createBorrowRequest(BorrowRequestDTO requestDTO);
BorrowingRecordDTO approveBorrowingRequest(Long recordId);
BorrowingRecordDTO rejectBorrowingRequest(Long recordId);
BorrowingRecordDTO returnBook(ReturnBookRequestDTO requestDTO);
List<BorrowingRecordDTO> getBorrowingHistory(Long memberId);
List<BorrowingRecordDTO> getPendingRequests();
List<BorrowingRecordDTO> getOverdueRecords();
```

### FineService

```java
FineDTO calculateOverdueFine(Long borrowingRecordId);
FineDTO getFineById(Long fineId);
List<FineDTO> getUnpaidFines();
FineDTO markFineAsPaid(Long fineId);
```

### PaymentService

```java
PaymentDTO payFine(PaymentRequestDTO requestDTO);
PaymentDTO getPaymentById(Long paymentId);
PaymentDTO getPaymentByFineId(Long fineId);
```

---

## 15. Controller Layer

Package:

```text
com.example.library.web.controller
```

Controllers should:

- Use `@RestController`
- Use `@RequestMapping`
- Use `ResponseEntity`
- Call service methods
- Avoid business logic

### BookController

Base path:

```text
/api/books
```

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/books/search?keyword=` | Search books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Create book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |

### MemberController

Base path:

```text
/api/members
```

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/members` | Get all members |
| GET | `/api/members/{id}` | Get member by ID |
| POST | `/api/members` | Create member |
| PUT | `/api/members/{id}` | Update member |
| PATCH | `/api/members/{id}/deactivate` | Deactivate member |
| GET | `/api/members/{id}/borrowing-history` | View borrowing history |

### BorrowingController

Base path:

```text
/api/borrowings
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/borrowings/requests` | Create borrow request |
| PATCH | `/api/borrowings/{recordId}/approve` | Approve borrow request |
| PATCH | `/api/borrowings/{recordId}/reject` | Reject borrow request |
| POST | `/api/borrowings/return` | Return book |
| GET | `/api/borrowings/pending` | Get pending requests |
| GET | `/api/borrowings/overdue` | Get overdue records |
| GET | `/api/borrowings/member/{memberId}` | Get member borrowing history |

### FineController

Base path:

```text
/api/fines
```

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/fines/{fineId}` | Get fine by ID |
| GET | `/api/fines/unpaid` | Get unpaid fines |
| POST | `/api/fines/{recordId}/calculate` | Calculate overdue fine |
| POST | `/api/fines/pay` | Pay fine |

### AuthController

Base path:

```text
/api/auth
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | Login placeholder |
| POST | `/api/auth/logout` | Logout placeholder |

---

## 16. DTO Layer

Package:

```text
com.example.library.web.dto
```

### DTO Classes

| DTO | Fields |
|---|---|
| BookDTO | bookId, title, author, isbn, publisher, publicationYear, categoryName, status |
| BookRequestDTO | title, author, isbn, publisher, publicationYear, categoryId |
| MemberDTO | memberId, fullName, email, phone, address, status |
| MemberRequestDTO | fullName, username, email, phone, address |
| BorrowRequestDTO | memberId, bookId |
| BorrowingRecordDTO | recordId, memberId, memberName, bookId, bookTitle, borrowDate, dueDate, returnDate, status, fineAmount |
| ReturnBookRequestDTO | recordId, returnDate |
| FineDTO | fineId, borrowingRecordId, amount, reason, status |
| PaymentDTO | paymentId, fineId, amount, paymentMethod, status, transactionCode, paymentDate |
| PaymentRequestDTO | fineId, amount, paymentMethod |
| ErrorResponseDTO | timestamp, status, error, message, path |

---

## 17. Exception Handling

Package:

```text
com.example.library.business.exception
```

### Custom Exceptions

| Exception | Purpose |
|---|---|
| ResourceNotFoundException | Used when book, member, record, fine, or payment is not found. |
| BookUnavailableException | Used when a book cannot be borrowed. |
| InvalidBorrowingRequestException | Used when borrow or return request is invalid. |
| OverdueFineException | Used when fine calculation fails. |
| PaymentFailedException | Used when payment fails or is invalid. |

### Global Exception Handler

Package:

```text
com.example.library.web.exception_handler
```

Class:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
}
```

Recommended HTTP status mapping:

| Exception | HTTP Status |
|---|---|
| ResourceNotFoundException | 404 NOT FOUND |
| BookUnavailableException | 400 BAD REQUEST |
| InvalidBorrowingRequestException | 400 BAD REQUEST |
| OverdueFineException | 400 BAD REQUEST |
| PaymentFailedException | 400 BAD REQUEST |
| Exception | 500 INTERNAL SERVER ERROR |

---

## 18. Design Patterns

## 18.1 Repository Pattern

The Repository Pattern separates database access logic from business logic.

Used in:

- BookRepository
- MemberRepository
- BorrowingRepository
- FineRepository
- PaymentRepository

Benefits:

- Cleaner services
- Easier testing
- Easier database query management

## 18.2 Service Layer Pattern

The Service Layer Pattern keeps business logic in dedicated service classes.

Used in:

- BookService
- MemberService
- BorrowingService
- FineService
- PaymentService

Benefits:

- Thin controllers
- Centralized business rules
- Better maintainability

## 18.3 Strategy Pattern for Fine Calculation

The Strategy Pattern is used for flexible fine calculation logic.

Main class:

```text
FineCalculationRule
```

Simple rule:

```text
Fine amount = overdue days × daily fine rate
```

Example daily rate:

```text
5,000 VND per overdue day
```

Future extensions:

- Different fine rules by book category
- Different fine rules by member type
- Grace period
- Maximum fine limit
- Fine discount

---

## 19. Source Code Generation Instructions for GitHub Copilot

GitHub Copilot should follow these instructions:

### General Instructions

- Use Java Spring Boot.
- Use Jakarta Persistence.
- Use Spring Data JPA.
- Use RESTful API.
- Use constructor injection.
- Use DTOs for request and response.
- Generate template code only.
- Add TODO comments where detailed business logic should be implemented.
- Do not implement full authentication or security.
- Focus on borrow book and return book flow.
- Keep controllers thin.
- Keep business logic in services.
- Keep database logic in repositories.
- Use custom exceptions.
- Use global exception handling.
- Use `BigDecimal` for money.
- Use `LocalDate` and `LocalDateTime` for dates.
- Do not expose entity classes directly from REST controllers.
- Do not use field injection with `@Autowired`.

### Entity Instructions

- Use `@Entity`.
- Use `@Table`.
- Use `@Id`.
- Use `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
- Use `@Enumerated(EnumType.STRING)` for enums.
- Use JPA relationships:
  - `@OneToOne`
  - `@OneToMany`
  - `@ManyToOne`
  - `@JoinColumn`
- Add constructors, getters, and setters.
- Add TODO comments for helper methods.

### Repository Instructions

- Extend `JpaRepository<Entity, Long>`.
- Add useful query methods.
- Use `@Query` only when useful.
- Keep repositories simple.

### Service Instructions

- Use `@Service`.
- Use constructor injection.
- Validate input.
- Throw custom exceptions.
- Convert entities to DTOs.
- Add TODO comments for unfinished logic.

### Controller Instructions

- Use `@RestController`.
- Use `@RequestMapping`.
- Use `@GetMapping`.
- Use `@PostMapping`.
- Use `@PutMapping`.
- Use `@PatchMapping`.
- Use `@DeleteMapping`.
- Return `ResponseEntity`.
- Do not write business logic in controllers.

### Security Instructions

- Create `AuthController` with login/logout placeholders only.
- Create `SecurityConfig` as placeholder if needed.
- Do not implement JWT.
- Do not implement role-based authorization.
- Do not implement full password encryption logic unless requested later.

---

## 20. Expected Source Code Files

```text
src/main/java/com/example/library/LibraryManagementApplication.java

src/main/java/com/example/library/config/AppConfig.java
src/main/java/com/example/library/config/SecurityConfig.java
src/main/java/com/example/library/config/DatabaseConfig.java

src/main/java/com/example/library/web/controller/AuthController.java
src/main/java/com/example/library/web/controller/BookController.java
src/main/java/com/example/library/web/controller/MemberController.java
src/main/java/com/example/library/web/controller/BorrowingController.java
src/main/java/com/example/library/web/controller/FineController.java

src/main/java/com/example/library/web/dto/BookDTO.java
src/main/java/com/example/library/web/dto/BookRequestDTO.java
src/main/java/com/example/library/web/dto/MemberDTO.java
src/main/java/com/example/library/web/dto/MemberRequestDTO.java
src/main/java/com/example/library/web/dto/BorrowRequestDTO.java
src/main/java/com/example/library/web/dto/BorrowingRecordDTO.java
src/main/java/com/example/library/web/dto/ReturnBookRequestDTO.java
src/main/java/com/example/library/web/dto/FineDTO.java
src/main/java/com/example/library/web/dto/PaymentDTO.java
src/main/java/com/example/library/web/dto/PaymentRequestDTO.java
src/main/java/com/example/library/web/dto/ErrorResponseDTO.java

src/main/java/com/example/library/web/exception_handler/GlobalExceptionHandler.java

src/main/java/com/example/library/business/service/BookService.java
src/main/java/com/example/library/business/service/MemberService.java
src/main/java/com/example/library/business/service/BorrowingService.java
src/main/java/com/example/library/business/service/FineService.java
src/main/java/com/example/library/business/service/PaymentService.java

src/main/java/com/example/library/business/rules/BorrowingPolicy.java
src/main/java/com/example/library/business/rules/FineCalculationRule.java

src/main/java/com/example/library/business/exception/ResourceNotFoundException.java
src/main/java/com/example/library/business/exception/BookUnavailableException.java
src/main/java/com/example/library/business/exception/InvalidBorrowingRequestException.java
src/main/java/com/example/library/business/exception/OverdueFineException.java
src/main/java/com/example/library/business/exception/PaymentFailedException.java

src/main/java/com/example/library/data/entity/UserAccount.java
src/main/java/com/example/library/data/entity/Member.java
src/main/java/com/example/library/data/entity/Librarian.java
src/main/java/com/example/library/data/entity/Book.java
src/main/java/com/example/library/data/entity/Category.java
src/main/java/com/example/library/data/entity/BorrowingRecord.java
src/main/java/com/example/library/data/entity/Fine.java
src/main/java/com/example/library/data/entity/Payment.java

src/main/java/com/example/library/data/repository/UserAccountRepository.java
src/main/java/com/example/library/data/repository/MemberRepository.java
src/main/java/com/example/library/data/repository/LibrarianRepository.java
src/main/java/com/example/library/data/repository/BookRepository.java
src/main/java/com/example/library/data/repository/CategoryRepository.java
src/main/java/com/example/library/data/repository/BorrowingRepository.java
src/main/java/com/example/library/data/repository/FineRepository.java
src/main/java/com/example/library/data/repository/PaymentRepository.java

src/main/java/com/example/library/data/enums/UserRole.java
src/main/java/com/example/library/data/enums/AccountStatus.java
src/main/java/com/example/library/data/enums/BookStatus.java
src/main/java/com/example/library/data/enums/BorrowingStatus.java
src/main/java/com/example/library/data/enums/FineStatus.java
src/main/java/com/example/library/data/enums/PaymentStatus.java
src/main/java/com/example/library/data/enums/PaymentMethod.java
```

---

## 21. Recommended Development Order

Copilot should generate files in this order:

1. Enum classes
2. Entity classes
3. Repository interfaces
4. DTO classes
5. Custom exception classes
6. Business rule classes
7. Service classes
8. Controller classes
9. Global exception handler
10. Configuration classes
11. Main Spring Boot application class

---

## 22. Final Notes

This design is intended for generating source code templates, not a complete production system.

The most important flows are:

- Search books
- Create borrow request
- Approve borrowing request
- Return book
- Calculate overdue fine
- Pay fine

Advanced features can be added later, such as:

- Real authentication and authorization
- JWT security
- External payment gateway integration
- Email notifications
- Swagger/OpenAPI documentation
- Unit tests
- Integration tests
- Frontend UI
