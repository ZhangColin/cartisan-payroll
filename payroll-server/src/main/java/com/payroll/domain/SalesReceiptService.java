package com.payroll.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesReceiptService {
    private EmployeeRepository employeeRepository;

    public SalesReceiptService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addSalesReceipt(Long employeeId, LocalDate date, BigDecimal amount) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("No such employee"));

        if (!(employee.getClassification() instanceof CommissionedClassification)) {
            throw new RuntimeException("Tried to add sales receipt to non-commissioned employee");
        }

        CommissionedClassification classification = (CommissionedClassification) employee.getClassification();

        classification.addSalesReceipt(new SalesReceipt(date, amount));
    }
}
