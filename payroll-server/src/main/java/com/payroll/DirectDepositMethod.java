package com.payroll;

import com.payroll.domain.PaymentMethod;

public class DirectDepositMethod implements PaymentMethod {
    private final String bank;
    private final String accountNumber;

    public DirectDepositMethod(String bank, String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}
