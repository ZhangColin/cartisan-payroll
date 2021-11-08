package com.payroll.application;

import com.payroll.domain.Employee;
import com.payroll.domain.EmployeeRepository;
import com.payroll.domain.EmployeeFactory;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ChangeEmployeeAppServiceTest {
    private final EmployeeRepository repository = mock(EmployeeRepository.class);
    private final EmployeeFactory employeeFactory = new EmployeeFactory();
    private final ChangeEmployeeAppService appService;

    public ChangeEmployeeAppServiceTest() {
        appService = new ChangeEmployeeAppService(repository);
    }

    @Test
    public void testChangeName() {
        // given
        Employee hourlyEmployee = employeeFactory.createHourlyEmployee("Bill", "Home", BigDecimal.valueOf(15.25));

        when(repository.findById(hourlyEmployee.getId())).thenReturn(Optional.of(hourlyEmployee));

        // when
        appService.changeName(hourlyEmployee.getId(), "Colin");

        // then
        verify(repository).save(argThat(employee -> employee.getName().equals("Colin")));
    }

    @Test
    public void testDeleteEmployee() {
        // when
        appService.removeEmployee(1L);

        // then
        verify(repository, atMostOnce()).deleteById(anyLong());
    }
}