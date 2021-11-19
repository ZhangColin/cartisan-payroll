package com.payroll.domain;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PayService {
    private final EmployeeRepository employeeRepository;
    private final AffiliationRepository affiliationRepository;

    public PayService(EmployeeRepository employeeRepository, AffiliationRepository affiliationRepository) {
        this.employeeRepository = employeeRepository;
        this.affiliationRepository = affiliationRepository;
    }

    public List<PayCheck> pay(LocalDate payDate) {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .filter(employee -> employee.isPayDate(payDate))
                .map(employee ->  {
                    PayCheck payCheck = new PayCheck(employee.getPayPeriodStartDate(payDate), payDate);

                    BigDecimal grossPay = employee.calculatePay(payCheck);

                    List<UnionAffiliation> affiliations = affiliationRepository.findByEmployeeId(employee.getId());

                    BigDecimal deductions = BigDecimal.ZERO;
                    for (UnionAffiliation affiliation : affiliations) {
                         deductions= deductions.add(affiliation.calculateDeductions(payCheck));
                    }

                    BigDecimal netPay = grossPay.subtract(deductions);

                    payCheck.setGrossPay(grossPay);
                    payCheck.setDeductions(deductions);
                    payCheck.setNetPay(netPay);

                    employee.pay(payCheck);

                    return payCheck;
                })
                .collect(toList());
    }
}
