package com.payroll.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return payDate.getDayOfWeek()== DayOfWeek.FRIDAY;
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return payDate.plusDays(-6);
    }
}
