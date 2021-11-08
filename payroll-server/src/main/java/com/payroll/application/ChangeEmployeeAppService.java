package com.payroll.application;

import com.payroll.domain.Employee;
import com.payroll.domain.EmployeeRepository;

public class ChangeEmployeeAppService {
    private EmployeeRepository employeeRepository;

    public ChangeEmployeeAppService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void changeName(Long employeeId, String name) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("No such employee"));

        employee.changeName(name);

        employeeRepository.save(employee);
    }

    public void removeEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


}
