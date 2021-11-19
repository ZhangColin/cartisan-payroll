package com.payroll.domain;

import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule{
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return isLastDayOfMonth(payDate);
    }

    private boolean isLastDayOfMonth(LocalDate payDate) {
        return payDate.getMonthValue()!=payDate.plusDays(1).getMonthValue();
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return LocalDate.of(payDate.getYear(), payDate.getMonth(), 1);
    }
}
