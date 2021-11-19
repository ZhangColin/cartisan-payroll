package com.payroll.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BiweeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return payDate.getDayOfWeek() == DayOfWeek.FRIDAY && payDate.getDayOfYear() % 2 == 0;
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return payDate.plusDays(-13);
    }
}
