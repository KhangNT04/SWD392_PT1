package com.example.library.data.repository;

import com.example.library.data.entity.Book;
import com.example.library.data.enums.BookStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategory_CategoryNameContainingIgnoreCase(String categoryName);
    List<Book> findByStatus(BookStatus status);

    @Query("""
            select b from Book b
            left join b.category c
            where lower(b.title) like lower(concat('%', :keyword, '%'))
               or lower(b.author) like lower(concat('%', :keyword, '%'))
               or lower(coalesce(c.categoryName, '')) like lower(concat('%', :keyword, '%'))
            """)
    List<Book> searchBooks(@Param("keyword") String keyword);
}
