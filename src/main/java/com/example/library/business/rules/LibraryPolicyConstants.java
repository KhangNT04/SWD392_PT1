package com.example.library.business.rules;

import java.math.BigDecimal;

public final class LibraryPolicyConstants {

    public static final long DEFAULT_BORROW_DAYS = 14L;
    public static final BigDecimal DEFAULT_DAILY_FINE_RATE = BigDecimal.valueOf(5000L);

    private LibraryPolicyConstants() {
    }
}
