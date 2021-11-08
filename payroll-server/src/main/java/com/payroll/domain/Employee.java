package com.payroll.domain;

import com.cartisan.domain.AbstractEntity;
import com.cartisan.domain.AggregateRoot;
import lombok.Getter;

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
}
