package com.payroll.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceChargeService {
    private AffiliationRepository affiliationRepository;

    public ServiceChargeService(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }

    public void addUnionMember(Long memberId, Long employeeId, BigDecimal dues) {
        if (!affiliationRepository.existsByMemberIdAndEmployeeId(memberId, employeeId)) {
            UnionAffiliation unionAffiliation = new UnionAffiliation(memberId, employeeId, dues);

            affiliationRepository.save(unionAffiliation);
        }
    }

    public void removeUnionMember(Long memberId, Long employeeId) {
        affiliationRepository.removeByMemberIdAndEmployeeId(memberId, employeeId);
    }



    public void addServiceCharge(Long memberId, Long employeeId, LocalDate date, BigDecimal amount) {
        UnionAffiliation unionAffiliation = affiliationRepository.findByMemberIdAndEmployeeId(memberId, employeeId)
                .orElseThrow(() -> new RuntimeException("Tries to add service charge to union member without a union affiliation"));


        unionAffiliation.addServiceCharge(new ServiceCharge(date, amount));

        affiliationRepository.save(unionAffiliation);
    }
}
