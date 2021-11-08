package com.payroll.domain;

import lombok.Data;

import java.time.LocalDate;

/**
 * 考勤卡
 */
@Data
public class TimeCard {
    private LocalDate date;
    private double hours;

    public TimeCard(LocalDate date, double hours) {
        this.date = date;
        this.hours = hours;
    }
}
