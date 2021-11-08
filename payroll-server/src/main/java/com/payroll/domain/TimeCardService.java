package com.payroll.domain;

import java.time.LocalDate;

public class TimeCardService {
    private EmployeeRepository employeeRepository;

    public TimeCardService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addTimeCard(Long employeeId, LocalDate date, double hours) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("No such employee"));

        if (!(employee.getClassification() instanceof HourlyClassification)) {
            throw new RuntimeException("Tried to add timecard to non-hourly employee");
        }

        HourlyClassification classification = (HourlyClassification)employee.getClassification();

        classification.addTimeCard(new TimeCard(date, hours));
    }
}
