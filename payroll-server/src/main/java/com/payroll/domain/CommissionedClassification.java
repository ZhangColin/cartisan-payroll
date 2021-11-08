package com.payroll.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 领销售提成的员工
 */
@Data
public class CommissionedClassification extends PaymentClassification {
    private BigDecimal commissionRate;
    private BigDecimal salary;
    private Map<LocalDate, SalesReceipt> salesReceipts = new HashMap<LocalDate, SalesReceipt>();

    public CommissionedClassification(BigDecimal salary, BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
        this.salary = salary;
    }

    public SalesReceipt getSalesReceipt(LocalDate date) {
        return salesReceipts.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceipts.putIfAbsent(salesReceipt.getDate(), salesReceipt);
    }
}
