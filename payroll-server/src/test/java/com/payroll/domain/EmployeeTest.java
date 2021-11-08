package com.payroll.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {
    private final EmployeeFactory employeeService;

    public EmployeeTest() {
        this.employeeService = new EmployeeFactory();
    }

    @Test
    public void testChangeName() {
        // given
        Employee employee = employeeService.createHourlyEmployee( "colin", "home", BigDecimal.valueOf(15.25));

        // when
        employee.changeName("zhang.colin");

        // then
        assertThat(employee.getName()).isEqualTo("zhang.colin");
    }
}