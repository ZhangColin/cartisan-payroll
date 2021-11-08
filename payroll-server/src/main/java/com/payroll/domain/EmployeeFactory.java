package com.payroll.domain;

import com.cartisan.util.SnowflakeIdWorker;
import com.payroll.DirectDepositMethod;
import com.payroll.MailMethod;
import com.payroll.WeeklySchedule;

import java.math.BigDecimal;

public class EmployeeFactory {
    private final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1,1);

    public Employee createSalariedEmployee(String name, String address, BigDecimal salary){
        return create(name, address,
                new SalariedClassification(salary),
                new MonthlySchedule(),
                new HoldMethod());
    }

    public void changeSalaried(Employee employee, BigDecimal salary){
        employee.changeClassification(new SalariedClassification(salary), new MonthlySchedule());
    }

    public Employee createHourlyEmployee(String name, String address, BigDecimal hourlyRate) {
        return create(name, address,
                new HourlyClassification(hourlyRate),
                new WeeklySchedule(),
                new HoldMethod());
    }

    public void changeHourly(Employee employee, BigDecimal hourlyRate){
        employee.changeClassification(new HourlyClassification(hourlyRate), new WeeklySchedule());
    }

    public Employee createCommissionedEmployee(String name, String address, BigDecimal salary, BigDecimal commissionRate) {
        return create(name, address,
                new CommissionedClassification(salary, commissionRate),
                new BiweeklySchedule(),
                new HoldMethod());
    }

    public void changeCommissioned(Employee employee, BigDecimal salary, BigDecimal commissionRate){
        employee.changeClassification(new CommissionedClassification(salary, commissionRate), new BiweeklySchedule());
    }

    private Employee create(String name, String address,
                            PaymentClassification classification, PaymentSchedule schedule, PaymentMethod paymentMethod) {
        return new Employee(idWorker.nextId(), name, address, classification, schedule, paymentMethod);
    }

    public void changeDirectMethod(Employee employee){
        employee.changeMethod(new DirectDepositMethod("Bank -1", "123"));
    }

    public void changeMailMethod(Employee employee) {
        employee.changeMethod(new MailMethod("3.14 Pi St"));
    }

    public void changeHoldMethod(Employee employee) {
        employee.changeMethod(new HoldMethod());
    }
}
