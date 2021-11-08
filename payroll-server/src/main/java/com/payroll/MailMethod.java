package com.payroll;

import com.payroll.domain.PaymentMethod;

public class MailMethod implements PaymentMethod {
    private final String address;

    public MailMethod(String address) {
        this.address = address;
    }
}
