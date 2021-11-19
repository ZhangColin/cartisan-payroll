package com.payroll.domain;

import com.cartisan.domain.AbstractEntity;
import com.cartisan.domain.AggregateRoot;
import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.payroll.domain.DateUtil.isInPayPeriod;

/**
 *
 */
@Data
public class UnionAffiliation extends AbstractEntity implements AggregateRoot {
    private Long memberId;
    private Long employeeId;
    private BigDecimal dues;
    private Map<LocalDate, ServiceCharge> serviceCharges = new HashMap<>();

    public UnionAffiliation(Long memberId, Long employeeId, BigDecimal dues) {
        this.memberId = memberId;
        this.employeeId = employeeId;
        this.dues = dues;
    }

    public ServiceCharge getServiceCharge(LocalDate date) {
        return serviceCharges.get(date);
    }

    public void addServiceCharge(ServiceCharge serviceCharge) {
        serviceCharges.putIfAbsent(serviceCharge.getDate(), serviceCharge);
    }

    public BigDecimal calculateDeductions(PayCheck payCheck) {
        int fridaysInPayPeriod = numberOfFridaysInPayPeriod(payCheck.getPayPeriodStartDate(), payCheck.getPayPeriodEndDate());

        BigDecimal totalDues  = dues.multiply(BigDecimal.valueOf(fridaysInPayPeriod));

        for (ServiceCharge serviceCharge : serviceCharges.values()) {
            if (isInPayPeriod(serviceCharge.getDate(), payCheck.getPayPeriodStartDate(), payCheck.getPayPeriodEndDate())) {
                totalDues = totalDues.add(serviceCharge.getAmount());
            }
        }

        return totalDues;
    }

    private int numberOfFridaysInPayPeriod(LocalDate payPeriodStart, LocalDate payPeriodEnd) {
        int fridays = 0;
        for (LocalDate day = payPeriodStart; day.isBefore(payPeriodEnd)||day.isEqual(payPeriodEnd); day = day.plusDays(1)){
            if (day.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays++;
            }
        }

        return fridays;
    }
}
