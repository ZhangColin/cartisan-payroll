package com.payroll.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 月薪员工
 */
@Data
public class SalariedClassification extends PaymentClassification {
    public SalariedClassification(BigDecimal salary) {
        this.salary = salary;
    }

    private BigDecimal salary;
}
