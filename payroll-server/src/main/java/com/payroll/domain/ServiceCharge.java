package com.payroll.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 服务费
 */
@Getter
public class ServiceCharge {
    private final LocalDate date;
    private final BigDecimal amount;

    public ServiceCharge(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }
}
