package com.example.library.data.repository;

import com.example.library.data.entity.Member;
import com.example.library.data.enums.AccountStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserAccount_Username(String username);
    Optional<Member> findByUserAccount_Email(String email);
    List<Member> findByStatus(AccountStatus status);
}
