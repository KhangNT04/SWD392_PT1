package com.example.library.business.rules;

import java.math.BigDecimal;

@FunctionalInterface
public interface FineCalculationRule {

    BigDecimal calculate(long overdueDays);
}
