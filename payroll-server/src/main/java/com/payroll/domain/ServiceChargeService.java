package com.payroll.domain;

import com.payroll.ServiceCharge;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceChargeService {
    private AffiliationRepository affiliationRepository;

    public ServiceChargeService(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }

    public void addUnionMember(Long memberId, Employee e) {

    }

    public void addServiceCharge(Long memberId, Long employeeId, LocalDate date, BigDecimal amount) {
        Affiliation affiliation = affiliationRepository.findByMemberIdAndEmployeeId(memberId, employeeId)
                .orElseThrow(() -> new RuntimeException("Tries to add service charge to union member without a union affiliation"));

        UnionAffiliation unionAffiliation = (UnionAffiliation)affiliation;

        unionAffiliation.addServiceCharge(new ServiceCharge(date, amount));
    }
}
