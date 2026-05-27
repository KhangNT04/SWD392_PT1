package com.example.library.business.service;

import com.example.library.business.exception.ResourceNotFoundException;
import com.example.library.data.entity.Book;
import com.example.library.data.entity.Category;
import com.example.library.data.enums.BookStatus;
import com.example.library.data.repository.BookRepository;
import com.example.library.data.repository.CategoryRepository;
import com.example.library.web.dto.BookDTO;
import com.example.library.web.dto.BookRequestDTO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<BookDTO> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword == null ? "" : keyword.trim()).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookDTO getBookById(Long bookId) {
        return toDto(getBookEntityById(bookId));
    }

    @Transactional
    public BookDTO createBook(BookRequestDTO requestDTO) {
        Book book = new Book();
        applyRequest(book, requestDTO);
        book.setStatus(BookStatus.AVAILABLE);
        return toDto(bookRepository.save(book));
    }

    @Transactional
    public BookDTO updateBook(Long bookId, BookRequestDTO requestDTO) {
        Book book = getBookEntityById(bookId);
        applyRequest(book, requestDTO);
        return toDto(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookRepository.delete(getBookEntityById(bookId));
    }

    @Transactional(readOnly = true)
    public boolean isBookAvailable(Long bookId) {
        return getBookEntityById(bookId).isAvailable();
    }

    @Transactional
    public void updateBookStatus(Long bookId, BookStatus status) {
        Book book = getBookEntityById(bookId);
        book.updateStatus(status);
        bookRepository.save(book);
    }

    private Book getBookEntityById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    private void applyRequest(Book book, BookRequestDTO requestDTO) {
        book.setTitle(requestDTO.getTitle());
        book.setAuthor(requestDTO.getAuthor());
        book.setIsbn(requestDTO.getIsbn());
        book.setPublisher(requestDTO.getPublisher());
        book.setPublicationYear(requestDTO.getPublicationYear());
        book.setCategory(getCategory(requestDTO.getCategoryId()));
        // TODO: Add duplicate ISBN validation if needed.
    }

    private BookDTO toDto(Book book) {
        return new BookDTO(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getCategory() != null ? book.getCategory().getCategoryName() : null,
                book.getStatus());
    }
}
