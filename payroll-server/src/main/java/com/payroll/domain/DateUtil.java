package com.payroll.domain;

import java.time.LocalDate;

public class DateUtil {
    public static boolean isInPayPeriod(
            LocalDate theDate, LocalDate startDate, LocalDate endDate) {
        return (theDate.isAfter(startDate) || theDate.isEqual(startDate))
                && (theDate.isBefore(endDate) || theDate.isEqual(endDate));
    }
}
