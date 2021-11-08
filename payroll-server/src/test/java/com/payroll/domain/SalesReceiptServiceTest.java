package com.payroll.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SalesReceiptServiceTest {
    @Test
    public void should_addSalesReceipt_success() {
        // given
        final EmployeeRepository repository = mock(EmployeeRepository.class);
        SalesReceiptService salesReceiptService = new SalesReceiptService(repository);

        EmployeeFactory employeeService = new EmployeeFactory();
        Employee employee = employeeService.createCommissionedEmployee("Bill", "Home", new BigDecimal("2000"), new BigDecimal("15.25"));

        when(repository.findById(anyLong())).thenReturn(Optional.of(employee));

        // when
        salesReceiptService.addSalesReceipt(1L, LocalDate.of(2021, 7, 31), new BigDecimal("250"));

        CommissionedClassification commissionedClassification = (CommissionedClassification) employee.getClassification();
        SalesReceipt salesReceipt = commissionedClassification.getSalesReceipt(LocalDate.of(2021, 7, 31));

        // then
        assertThat(salesReceipt).isNotNull();
        assertThat(salesReceipt.getAmount()).isEqualTo(new BigDecimal("250"));
    }
}