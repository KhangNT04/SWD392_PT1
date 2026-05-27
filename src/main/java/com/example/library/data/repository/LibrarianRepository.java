package com.example.library.data.repository;

import com.example.library.data.entity.Librarian;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {

    Optional<Librarian> findByEmployeeCode(String employeeCode);
    Optional<Librarian> findByUserAccount_Username(String username);
}
