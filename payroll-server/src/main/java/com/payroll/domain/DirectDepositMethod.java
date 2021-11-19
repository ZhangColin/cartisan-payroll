package com.payroll.domain;

import lombok.Data;
import lombok.Getter;

@Getter
public class DirectDepositMethod implements PaymentMethod {
    private final String bank;
    private final String accountNumber;

    public DirectDepositMethod(String bank, String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    @Override
    public void pay(PayCheck payCheck) {
        payCheck.setField("Disposition", "Direct");
    }
}
