package com.payroll.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售凭条
 */
@Getter
public class SalesReceipt {
    private final LocalDate date;
    private final BigDecimal amount;

    public SalesReceipt(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }
}
