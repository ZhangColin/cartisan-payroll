package com.payroll.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeFactoryTest {
    private final EmployeeFactory factory = new EmployeeFactory();

    @Test
    public void should_createSalariedEmployee_success() {
        // when
        Employee employee = factory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        // then
        assertThat(employee.getName()).isEqualTo("Bob");

        assertThat(employee.getClassification()).isInstanceOf(SalariedClassification.class);
        assertThat(((SalariedClassification)employee.getClassification()).getSalary()).isEqualTo(new BigDecimal("1000"));

        assertThat(employee.getSchedule()).isInstanceOf(MonthlySchedule.class);

        assertThat(employee.getMethod()).isInstanceOf(HoldMethod.class);
    }

    @Test
    public void should_changeSalaried_success() {
        // given
        Employee employee = factory.createHourlyEmployee("Micah", "Home", new BigDecimal("200"));

        // when
        factory.changeSalaried(employee, new BigDecimal("1000"));

        // then
        assertThat(employee.getClassification()).isInstanceOf(SalariedClassification.class);
        assertThat(((SalariedClassification)employee.getClassification()).getSalary()).isEqualTo(new BigDecimal("1000"));

        assertThat(employee.getSchedule()).isInstanceOf(MonthlySchedule.class);
    }

    @Test
    public void should_createHourlyEmployee_success() {
        // when
        Employee employee = factory.createHourlyEmployee("Micah", "Home", new BigDecimal("200"));

        // then
        assertThat(employee.getName()).isEqualTo("Micah");

        assertThat(employee.getClassification()).isInstanceOf(HourlyClassification.class);
        assertThat(((HourlyClassification)employee.getClassification()).getHourlyRate()).isEqualTo(new BigDecimal("200"));

        assertThat(employee.getSchedule()).isInstanceOf(WeeklySchedule.class);

        assertThat(employee.getMethod()).isInstanceOf(HoldMethod.class);
    }

    @Test
    public void should_changeHourly_success() {
        // given
        Employee employee = factory.createCommissionedEmployee("Justin", "Home", new BigDecimal("2500"), new BigDecimal("9.5"));

        // when
        factory.changeHourly(employee, new BigDecimal("200"));

        // then
        assertThat(employee.getClassification()).isInstanceOf(HourlyClassification.class);
        assertThat(((HourlyClassification)employee.getClassification()).getHourlyRate()).isEqualTo(new BigDecimal("200"));

        assertThat(employee.getSchedule()).isInstanceOf(WeeklySchedule.class);
    }

    @Test
    public void should_createCommissionedEmployee_success() {
        // when
        Employee employee = factory.createCommissionedEmployee("Justin", "Home", new BigDecimal("2500"), new BigDecimal("9.5"));

        // then
        assertThat(employee.getName()).isEqualTo("Justin");

        assertThat(employee.getClassification()).isInstanceOf(CommissionedClassification.class);
        CommissionedClassification commissionedClassification = (CommissionedClassification) employee.getClassification();
        assertThat(commissionedClassification.getBaseRate()).isEqualTo(new BigDecimal("2500"));
        assertThat(commissionedClassification.getCommissionRate()).isEqualTo(new BigDecimal("9.5"));

        assertThat(employee.getSchedule()).isInstanceOf(BiweeklySchedule.class);

        assertThat(employee.getMethod()).isInstanceOf(HoldMethod.class);
    }

    @Test
    public void should_changeCommissioned_success() {
        // given
        Employee employee = factory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        // when
        factory.changeCommissioned(employee, new BigDecimal("2500"), new BigDecimal("9.5"));

        // then
        assertThat(employee.getClassification()).isInstanceOf(CommissionedClassification.class);
        CommissionedClassification commissionedClassification = (CommissionedClassification) employee.getClassification();
        assertThat(commissionedClassification.getBaseRate()).isEqualTo(new BigDecimal("2500"));
        assertThat(commissionedClassification.getCommissionRate()).isEqualTo(new BigDecimal("9.5"));

        assertThat(employee.getSchedule()).isInstanceOf(BiweeklySchedule.class);
    }

    @Test
    public void should_changeDirectMethod_success() {
        // given
        Employee employee = factory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        // when
        factory.changeDirectMethod(employee);

        // then
        assertThat(employee.getMethod()).isInstanceOf(DirectDepositMethod.class);
    }

    @Test
    public void should_changeHoldMethod_success() {
        // given
        Employee employee = factory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        // when
        factory.changeHoldMethod(employee);

        // then
        assertThat(employee.getMethod()).isInstanceOf(HoldMethod.class);
    }

    @Test
    public void should_changeMailMethod_success() {
        // given
        Employee employee = factory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        // when
        factory.changeMailMethod(employee);

        // then
        assertThat(employee.getMethod()).isInstanceOf(MailMethod.class);
    }
}