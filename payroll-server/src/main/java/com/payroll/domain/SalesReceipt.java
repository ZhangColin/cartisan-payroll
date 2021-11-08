package com.payroll.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售凭条
 */
@Data
public class SalesReceipt {
    private LocalDate date;
    private BigDecimal amount;

    public SalesReceipt(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }
}
