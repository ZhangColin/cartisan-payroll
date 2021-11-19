package com.payroll.domain;

public class MailMethod implements PaymentMethod {
    private final String address;

    public MailMethod(String address) {
        this.address = address;
    }

    @Override
    public void pay(PayCheck payCheck) {
        payCheck.setField("Disposition", "Mail");
    }
}
