package com.example.library.data.repository;

import com.example.library.data.entity.Fine;
import com.example.library.data.enums.FineStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Long> {

    Optional<Fine> findByBorrowingRecord_RecordId(Long recordId);
    List<Fine> findByStatus(FineStatus status);
}
