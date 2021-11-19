package com.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Hashtable;

@Getter
public class PayCheck {
    private final LocalDate payDate;
    private final LocalDate payPeriodStartDate;
    @Setter
    private BigDecimal grossPay;
    private final Hashtable<String, String> fields = new Hashtable<>();
    @Setter
    private BigDecimal deductions;
    @Setter
    private BigDecimal netPay;

    public PayCheck(LocalDate payPeriodStartDate, LocalDate payDate) {
        this.payDate = payDate;
        this.payPeriodStartDate = payPeriodStartDate;
    }

    public void setField(String fieldName, String value) {
        fields.put(fieldName, value);
    }

    public String getField(String fieldName) {
        return fields.get(fieldName);
    }

    public LocalDate getPayPeriodEndDate() {
        return payDate;
    }
}
