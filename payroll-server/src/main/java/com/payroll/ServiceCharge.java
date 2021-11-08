package com.payroll;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 服务费
 */
@Data
public class ServiceCharge {
    private LocalDate date;
    private BigDecimal amount;

    public ServiceCharge(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }
}
