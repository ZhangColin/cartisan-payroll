package com.payroll.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeCardServiceTest {
    @Test
    public void should_addTimeCard_success() {
        // given
        final EmployeeRepository repository = mock(EmployeeRepository.class);
        TimeCardService timeCardService = new TimeCardService(repository);

        EmployeeFactory employeeService = new EmployeeFactory();
        Employee employee = employeeService.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(repository.findById(anyLong())).thenReturn(Optional.of(employee));

        // when
        timeCardService.addTimeCard(1L, LocalDate.of(2021, 7, 31), 8.0);

        HourlyClassification hourlyClassification = (HourlyClassification) employee.getClassification();
        TimeCard timeCard = hourlyClassification.getTimeCard(LocalDate.of(2021, 7, 31));

        // then
        assertThat(timeCard).isNotNull();
        assertThat(timeCard.getHours()).isEqualTo(8.0);
    }
}