package com.payroll.domain;

import com.cartisan.domain.AbstractEntity;
import com.cartisan.domain.AggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class Employee extends AbstractEntity implements AggregateRoot {
    public Employee(Long id, String name, String address, PaymentClassification classification, PaymentSchedule schedule, PaymentMethod method) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.classification = classification;
        this.schedule = schedule;
        this.method = method;
    }

    private Long id;
    private String name;
    private String address;
    private PaymentClassification classification;
    private PaymentSchedule schedule;
    private PaymentMethod method;

    public void changeName(String name){
        this.name = name;
    }

    public void changeClassification(PaymentClassification classification, PaymentSchedule schedule) {
        this.classification = classification;
        this.schedule = schedule;
    }

    public void changeMethod(PaymentMethod method) {
        this.method = method;
    }

    public boolean isPayDate(LocalDate payDate) {
        return schedule.isPayDate(payDate);
    }

    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return schedule.getPayPeriodStartDate(payDate);
    }

    public BigDecimal calculatePay(PayCheck payCheck) {
        return classification.calculatePay(payCheck);
    }

    public void pay(PayCheck payCheck) {
        method.pay(payCheck);
    }
}
