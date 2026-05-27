package com.example.library.config;

import com.example.library.business.exception.BookUnavailableException;
import com.example.library.business.exception.InvalidBorrowingRequestException;
import com.example.library.business.rules.BorrowingPolicy;
import com.example.library.business.rules.FineCalculationRule;
import com.example.library.data.entity.Book;
import com.example.library.data.entity.Member;
import com.example.library.data.enums.AccountStatus;
import java.math.BigDecimal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final BigDecimal DEFAULT_DAILY_FINE_RATE = BigDecimal.valueOf(5000L);

    @Bean
    public BorrowingPolicy borrowingPolicy() {
        return (Member member, Book book) -> {
            if (member == null || member.getStatus() != AccountStatus.ACTIVE) {
                throw new InvalidBorrowingRequestException("Member account is not active.");
            }
            if (book == null || !book.isAvailable()) {
                throw new BookUnavailableException("Book is not available for borrowing.");
            }
            // TODO: Add extra rules such as active-loan limits.
        };
    }

    @Bean
    public FineCalculationRule fineCalculationRule() {
        return overdueDays -> BigDecimal.valueOf(overdueDays).multiply(DEFAULT_DAILY_FINE_RATE);
    }
}
