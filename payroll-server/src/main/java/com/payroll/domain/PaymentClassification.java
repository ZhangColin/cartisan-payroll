package com.payroll.domain;

import java.math.BigDecimal;

public abstract class PaymentClassification {
    public abstract BigDecimal calculatePay(PayCheck payCheck);
}
