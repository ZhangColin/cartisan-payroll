package com.payroll;

import lombok.Data;

@Data
public class AddEmployeeCommand {
    private String employeeId;
    private String name;
    private String Address;

}
