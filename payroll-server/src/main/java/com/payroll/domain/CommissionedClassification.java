package com.payroll.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.payroll.domain.DateUtil.isInPayPeriod;

/**
 * 领销售提成的员工
 */
@Getter
public class CommissionedClassification extends PaymentClassification {
    private final BigDecimal commissionRate;
    private final BigDecimal baseRate;
    private final Map<LocalDate, SalesReceipt> salesReceipts = new HashMap<>();

    public CommissionedClassification(BigDecimal baseRate, BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
        this.baseRate = baseRate;
    }

    public SalesReceipt getSalesReceipt(LocalDate date) {
        return salesReceipts.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceipts.putIfAbsent(salesReceipt.getDate(), salesReceipt);
    }

    @Override
    public BigDecimal calculatePay(PayCheck payCheck) {
        BigDecimal salesTotal = BigDecimal.ZERO;

        for (SalesReceipt salesReceipt : salesReceipts.values()) {
            if (isInPayPeriod(salesReceipt.getDate(), payCheck.getPayPeriodStartDate(), payCheck.getPayPeriodEndDate())) {
                salesTotal = salesTotal.add(salesReceipt.getAmount());
            }
        }

        return baseRate.add(salesTotal.multiply(commissionRate).multiply(BigDecimal.valueOf(0.01)));
    }
}
