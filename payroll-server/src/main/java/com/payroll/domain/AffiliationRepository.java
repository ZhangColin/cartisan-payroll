package com.payroll.domain;

import com.cartisan.repository.BaseRepository;

import java.util.Optional;

public interface AffiliationRepository   extends BaseRepository<Affiliation, Long> {
    Optional<Affiliation> findByMemberIdAndEmployeeId(Long memberId, Long employeeId);
}
