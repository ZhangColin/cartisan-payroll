package com.payroll.domain;

import com.cartisan.domain.AbstractEntity;
import com.cartisan.domain.AggregateRoot;
import com.payroll.ServiceCharge;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Data
public class UnionAffiliation extends AbstractEntity implements AggregateRoot {
    private Long memberId;
    private Long employeeId;
    private BigDecimal dues;
    private Map<LocalDate, ServiceCharge> serviceCharges = new HashMap<>();

    public UnionAffiliation(Long memberId, BigDecimal dues) {
        this.memberId = memberId;
        this.dues = dues;
    }

    public ServiceCharge getServiceCharge(LocalDate date) {
        return serviceCharges.get(date);
    }

    public void addServiceCharge(ServiceCharge serviceCharge) {
        serviceCharges.putIfAbsent(serviceCharge.getDate(), serviceCharge);
    }
}
