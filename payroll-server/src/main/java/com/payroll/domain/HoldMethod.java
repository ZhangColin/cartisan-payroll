package com.payroll.domain;

public class HoldMethod implements PaymentMethod{
    @Override
    public void pay(PayCheck payCheck) {
        payCheck.setField("Disposition", "Hold");
    }
}
