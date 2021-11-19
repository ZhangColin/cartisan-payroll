package com.payroll.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PayServiceTest {
    private final EmployeeRepository employeeRepository;
    private final AffiliationRepository affiliationRepository;

    private final PayService payService;
    private final EmployeeFactory employeeFactory;

    public PayServiceTest() {
        employeeRepository = mock(EmployeeRepository.class);
        affiliationRepository = mock(AffiliationRepository.class);
        payService = new PayService(employeeRepository, affiliationRepository);
        employeeFactory = new EmployeeFactory();

        when(affiliationRepository.findByEmployeeId(anyLong())).thenReturn(new ArrayList<>());
    }

    @Test
    public void pay_single_salariedEmployee() {
        // given
        Employee employee = employeeFactory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));

        // when
        List<PayCheck> payChecks = payService.pay(LocalDate.of(2021, 11, 30));

        // then
        validatePayCheck(payChecks, LocalDate.of(2021, 11, 30), new BigDecimal("1000"));
    }

    @Test
    public void pay_single_salariedEmployeeOnWrongDate() {
        // given
        Employee employee = employeeFactory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(affiliationRepository.findByEmployeeId(anyLong())).thenReturn(new ArrayList<>());

        // when
        List<PayCheck> payChecks = payService.pay(LocalDate.of(2021, 11, 29));

        // then
        assertThat(payChecks.size()).isEqualTo(0);
    }

    @Test
    public void paying_single_hourlyEmployee_noTimeCards() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));

        // when
        List<PayCheck> payChecks = payService.pay(LocalDate.of(2021, 11, 12));      // Friday

        // then
        validatePayCheck(payChecks, LocalDate.of(2021, 11, 12), new BigDecimal("0"));
    }

    @Test
    public void paying_single_hourlyEmployee_oneTimeCard() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // Friday
        timeCardService.addTimeCard(employee.getId(), payDate, 2.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);  

        // then
        validatePayCheck(payChecks, payDate, new BigDecimal("30.5"));
    }

    @Test
    public void paying_single_hourlyEmployee_overtimeOneTimeCard() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // Friday
        timeCardService.addTimeCard(employee.getId(), payDate, 9.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);  

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf((8 + 1.5) * 15.25));
    }

    @Test
    public void paying_single_hourlyEmployee_onWrongDate() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 11);
        timeCardService.addTimeCard(employee.getId(), payDate, 9.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);  

        // then
        assertThat(payChecks.size()).isEqualTo(0);
    }

    @Test
    public void paying_single_hourlyEmployee_twoTimeCards() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // Friday
        timeCardService.addTimeCard(employee.getId(), payDate, 2.0);
        timeCardService.addTimeCard(employee.getId(), payDate.minusDays(1), 5.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);  

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf(7 * 15.25));
    }

    @Test
    public void paying_single_hourlyEmployee_with_timeCards_spanning_twoPayPeriods() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // Friday
        timeCardService.addTimeCard(employee.getId(), payDate, 2.0);
        timeCardService.addTimeCard(employee.getId(),LocalDate.of(2021, 10, 30), 5.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);  

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf(2 * 15.25));
    }

    @Test
    public void paying_single_commissionedEmployee_noReceipts() {
        // given
        Employee employee = employeeFactory.createCommissionedEmployee(
                "Bill", "Home", new BigDecimal("1500"), new BigDecimal("10"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));

        LocalDate payDate = LocalDate.of(2021, 11, 12);     // payday

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf(1500));
    }

    @Test
    public void paying_single_commissionedEmployee_oneReceipt() {
        // given
        Employee employee = employeeFactory.createCommissionedEmployee(
                "Bill", "Home", new BigDecimal("1500"), new BigDecimal("10"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        SalesReceiptService salesReceiptService = new SalesReceiptService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // payday
        salesReceiptService.addSalesReceipt(employee.getId(), payDate, BigDecimal.valueOf(5000));

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf(2000));
    }

    @Test
    public void paying_single_commissionedEmployee_onWrongDate() {
        // given
        Employee employee = employeeFactory.createCommissionedEmployee(
                "Bill", "Home", new BigDecimal("1500"), new BigDecimal("10"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        SalesReceiptService salesReceiptService = new SalesReceiptService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 11);
        salesReceiptService.addSalesReceipt(employee.getId(), payDate, BigDecimal.valueOf(5000));

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        assertThat(payChecks.size()).isEqualTo(0);
    }

    @Test
    public void paying_single_commissionedEmployee_twoReceipts() {
        // given
        Employee employee = employeeFactory.createCommissionedEmployee(
                "Bill", "Home", new BigDecimal("1500"), new BigDecimal("10"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        SalesReceiptService salesReceiptService = new SalesReceiptService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // payday
        salesReceiptService.addSalesReceipt(employee.getId(), payDate, BigDecimal.valueOf(5000));
        salesReceiptService.addSalesReceipt(employee.getId(), payDate.minusDays(1), BigDecimal.valueOf(3500));

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf(2350));
    }

    @Test
    public void paying_single_commissionedEmployee_withReceipts_spanning_twoPayPeriods() {
        // given
        Employee employee = employeeFactory.createCommissionedEmployee(
                "Bill", "Home", new BigDecimal("1500"), new BigDecimal("10"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        SalesReceiptService salesReceiptService = new SalesReceiptService(employeeRepository);
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // payday
        salesReceiptService.addSalesReceipt(employee.getId(), payDate, BigDecimal.valueOf(5000));
        salesReceiptService.addSalesReceipt(employee.getId(), payDate.minusDays(15), BigDecimal.valueOf(3500));

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        validatePayCheck(payChecks, payDate, BigDecimal.valueOf(2000));
    }

    @Test
    public void salariedUnionMemberDues() {
        // given
        Employee employee = employeeFactory.createSalariedEmployee("Bob", "Home", new BigDecimal("1000"));

        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        when(affiliationRepository.findByEmployeeId(anyLong())).thenReturn(
                asList(new UnionAffiliation(1L, employee.getId(), BigDecimal.valueOf(9.42))));

        // when
        LocalDate payDate = LocalDate.of(2021, 11, 30);     // PayDay
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        assertThat(payChecks.size()).isEqualTo(1);

        PayCheck payCheck = payChecks.get(0);
        assertThat(payCheck.getPayDate()).isEqualTo(payDate);
        assertThat(payCheck.getGrossPay().compareTo(BigDecimal.valueOf(1000))).isEqualTo(0);
        assertThat(payCheck.getField("Disposition")).isEqualTo("Hold");
        assertThat(payCheck.getDeductions().compareTo(BigDecimal.valueOf(37.68))).isEqualTo(0);
        assertThat(payCheck.getNetPay().compareTo(BigDecimal.valueOf(1000-37.68))).isEqualTo(0);
    }

    @Test
    public void hourlyUnionMemberServiceCharge() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));


        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        UnionAffiliation unionAffiliation = new UnionAffiliation(1L, employee.getId(), BigDecimal.valueOf(9.42));
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // PayDay
        unionAffiliation.addServiceCharge(new ServiceCharge(payDate, BigDecimal.valueOf(19.42)));
        when(affiliationRepository.findByEmployeeId(anyLong())).thenReturn(
                asList(unionAffiliation));

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        timeCardService.addTimeCard(employee.getId(), payDate, 8.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        assertThat(payChecks.size()).isEqualTo(1);

        PayCheck payCheck = payChecks.get(0);
        assertThat(payCheck.getPayDate()).isEqualTo(payDate);
        assertThat(payCheck.getGrossPay().compareTo(BigDecimal.valueOf(8*15.25))).isEqualTo(0);
        assertThat(payCheck.getField("Disposition")).isEqualTo("Hold");
        assertThat(payCheck.getDeductions().compareTo(BigDecimal.valueOf(28.84))).isEqualTo(0);
        assertThat(payCheck.getNetPay().compareTo(BigDecimal.valueOf((8*15.25)-28.84))).isEqualTo(0);
    }

    @Test
    public void serviceChargesSpanningMultiplePayPeriods() {
        // given
        Employee employee = employeeFactory.createHourlyEmployee("Bill", "Home", new BigDecimal("15.25"));


        when(employeeRepository.findAll()).thenReturn(singletonList(employee));
        UnionAffiliation unionAffiliation = new UnionAffiliation(1L, employee.getId(), BigDecimal.valueOf(9.42));
        LocalDate payDate = LocalDate.of(2021, 11, 12);     // PayDay
        unionAffiliation.addServiceCharge(new ServiceCharge(payDate.minusDays(7), BigDecimal.valueOf(100)));
        unionAffiliation.addServiceCharge(new ServiceCharge(payDate, BigDecimal.valueOf(19.42)));
        unionAffiliation.addServiceCharge(new ServiceCharge(payDate.plusDays(7), BigDecimal.valueOf(200)));
        when(affiliationRepository.findByEmployeeId(anyLong())).thenReturn(
                asList(unionAffiliation));

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        TimeCardService timeCardService = new TimeCardService(employeeRepository);
        timeCardService.addTimeCard(employee.getId(), payDate, 8.0);

        // when
        List<PayCheck> payChecks = payService.pay(payDate);

        // then
        assertThat(payChecks.size()).isEqualTo(1);

        PayCheck payCheck = payChecks.get(0);
        assertThat(payCheck.getPayDate()).isEqualTo(payDate);
        assertThat(payCheck.getGrossPay().compareTo(BigDecimal.valueOf(8*15.25))).isEqualTo(0);
        assertThat(payCheck.getField("Disposition")).isEqualTo("Hold");
        assertThat(payCheck.getDeductions().compareTo(BigDecimal.valueOf(28.84))).isEqualTo(0);
        assertThat(payCheck.getNetPay().compareTo(BigDecimal.valueOf((8*15.25)-28.84))).isEqualTo(0);
    }

    private void validatePayCheck(List<PayCheck> payChecks, LocalDate payDate, BigDecimal pay) {
        assertThat(payChecks.size()).isEqualTo(1);

        PayCheck payCheck = payChecks.get(0);
        assertThat(payCheck.getPayDate()).isEqualTo(payDate);
        assertThat(payCheck.getGrossPay().compareTo(pay)).isEqualTo(0);
        assertThat(payCheck.getField("Disposition")).isEqualTo("Hold");
        assertThat(payCheck.getDeductions()).isEqualTo(new BigDecimal("0"));
        assertThat(payCheck.getNetPay().compareTo(pay)).isEqualTo(0);
    }
}