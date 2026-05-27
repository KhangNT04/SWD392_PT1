package com.example.library.business.rules;

import com.example.library.data.entity.Book;
import com.example.library.data.entity.Member;

@FunctionalInterface
public interface BorrowingPolicy {

    void validateBorrowing(Member member, Book book);
}
