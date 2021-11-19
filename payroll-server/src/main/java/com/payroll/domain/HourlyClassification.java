package com.payroll.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static com.payroll.domain.DateUtil.isInPayPeriod;

/**
 * 钟点工
 */
@Getter
public class HourlyClassification extends PaymentClassification {
    private HashMap<LocalDate, TimeCard> timeCards = new HashMap<>();
    private BigDecimal hourlyRate;

    public HourlyClassification(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void addTimeCard(TimeCard timeCard) {
        timeCards.putIfAbsent(timeCard.getDate(), timeCard);
    }

    public TimeCard getTimeCard(LocalDate date) {
        return timeCards.get(date);
    }

    @Override
    public BigDecimal calculatePay(PayCheck payCheck) {
        BigDecimal totalPay = BigDecimal.ZERO;

        for (TimeCard timeCard : timeCards.values()) {
            if (isInPayPeriod(timeCard.getDate(), payCheck.getPayPeriodStartDate(), payCheck.getPayPeriodEndDate())) {
                totalPay = totalPay.add(calculatePayForTimeCart(timeCard));
            }
        }

        return totalPay;
    }

    private BigDecimal calculatePayForTimeCart(TimeCard timeCard) {
        double overtimeHours = Math.max(0.0, timeCard.getHours() - 8);
        double normalHours = timeCard.getHours() - overtimeHours;
        
        return hourlyRate.multiply(BigDecimal.valueOf(normalHours)).add(
                hourlyRate.multiply(BigDecimal.valueOf(1.5).multiply(BigDecimal.valueOf(overtimeHours))));
    }
}
