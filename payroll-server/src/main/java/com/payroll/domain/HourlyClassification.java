package com.payroll.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 钟点工
 */
@Data
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
}
