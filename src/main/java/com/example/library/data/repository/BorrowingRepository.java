package com.example.library.data.repository;

import com.example.library.data.entity.BorrowingRecord;
import com.example.library.data.enums.BorrowingStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findByMember_MemberId(Long memberId);
    List<BorrowingRecord> findByBook_BookId(Long bookId);
    List<BorrowingRecord> findByStatus(BorrowingStatus status);
    List<BorrowingRecord> findByMember_MemberIdAndStatus(Long memberId, BorrowingStatus status);
    List<BorrowingRecord> findByDueDateBeforeAndStatus(LocalDate date, BorrowingStatus status);
    boolean existsByBook_BookIdAndStatusIn(Long bookId, List<BorrowingStatus> statuses);
}
