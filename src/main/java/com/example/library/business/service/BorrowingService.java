package com.example.library.business.service;

import com.example.library.business.exception.BookUnavailableException;
import com.example.library.business.exception.InvalidBorrowingRequestException;
import com.example.library.business.exception.ResourceNotFoundException;
import com.example.library.business.rules.BorrowingPolicy;
import com.example.library.data.entity.Book;
import com.example.library.data.entity.BorrowingRecord;
import com.example.library.data.entity.Member;
import com.example.library.data.enums.BookStatus;
import com.example.library.data.enums.BorrowingStatus;
import com.example.library.data.repository.BookRepository;
import com.example.library.data.repository.BorrowingRepository;
import com.example.library.data.repository.MemberRepository;
import com.example.library.web.dto.BorrowRequestDTO;
import com.example.library.web.dto.BorrowingRecordDTO;
import com.example.library.web.dto.ReturnBookRequestDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final FineService fineService;
    private final BorrowingPolicy borrowingPolicy;

    public BorrowingService(BorrowingRepository borrowingRepository, MemberRepository memberRepository,
            BookRepository bookRepository, FineService fineService, BorrowingPolicy borrowingPolicy) {
        this.borrowingRepository = borrowingRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.fineService = fineService;
        this.borrowingPolicy = borrowingPolicy;
    }

    @Transactional
    public BorrowingRecordDTO createBorrowRequest(BorrowRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + requestDTO.getMemberId()));
        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + requestDTO.getBookId()));
        if (!book.isAvailable()) {
            throw new BookUnavailableException("Book is not available for borrowing.");
        }
        borrowingPolicy.validateBorrowing(member, book);

        BorrowingRecord record = new BorrowingRecord();
        record.setMember(member);
        record.setBook(book);
        record.setStatus(BorrowingStatus.PENDING);
        // TODO: Add richer request metadata if approval workflows expand.
        return BorrowingMapper.toDto(borrowingRepository.save(record));
    }

    @Transactional
    public BorrowingRecordDTO approveBorrowingRequest(Long recordId) {
        BorrowingRecord record = getRecord(recordId);
        if (record.getStatus() != BorrowingStatus.PENDING) {
            throw new InvalidBorrowingRequestException("Only pending requests can be approved.");
        }
        if (record.getBook() == null || !record.getBook().isAvailable()) {
            throw new BookUnavailableException("Book is not available for approval.");
        }

        record.setStatus(BorrowingStatus.BORROWED);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.getBook().setStatus(BookStatus.BORROWED);
        bookRepository.save(record.getBook());
        return BorrowingMapper.toDto(borrowingRepository.save(record));
    }

    @Transactional
    public BorrowingRecordDTO rejectBorrowingRequest(Long recordId) {
        BorrowingRecord record = getRecord(recordId);
        if (record.getStatus() != BorrowingStatus.PENDING) {
            throw new InvalidBorrowingRequestException("Only pending requests can be rejected.");
        }
        record.setStatus(BorrowingStatus.REJECTED);
        return BorrowingMapper.toDto(borrowingRepository.save(record));
    }

    @Transactional
    public BorrowingRecordDTO returnBook(ReturnBookRequestDTO requestDTO) {
        BorrowingRecord record = getRecord(requestDTO.getRecordId());
        if (record.getStatus() != BorrowingStatus.BORROWED) {
            throw new InvalidBorrowingRequestException("Only borrowed records can be returned.");
        }

        LocalDate returnDate = requestDTO.getReturnDate() != null ? requestDTO.getReturnDate() : LocalDate.now();
        record.setReturnDate(returnDate);
        record.setStatus(BorrowingStatus.RETURNED);
        if (record.getBook() != null) {
            record.getBook().setStatus(BookStatus.AVAILABLE);
            bookRepository.save(record.getBook());
        }
        BorrowingRecord saved = borrowingRepository.save(record);
        if (saved.isOverdue()) {
            fineService.calculateOverdueFine(saved.getRecordId());
            saved = borrowingRepository.findById(saved.getRecordId()).orElse(saved);
        }
        return BorrowingMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<BorrowingRecordDTO> getBorrowingHistory(Long memberId) {
        return borrowingRepository.findByMember_MemberId(memberId).stream()
                .map(BorrowingMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BorrowingRecordDTO> getPendingRequests() {
        return borrowingRepository.findByStatus(BorrowingStatus.PENDING).stream()
                .map(BorrowingMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BorrowingRecordDTO> getOverdueRecords() {
        return borrowingRepository.findByDueDateBeforeAndStatus(LocalDate.now(), BorrowingStatus.BORROWED).stream()
                .map(BorrowingMapper::toDto)
                .toList();
    }

    private BorrowingRecord getRecord(Long recordId) {
        return borrowingRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + recordId));
    }
}
