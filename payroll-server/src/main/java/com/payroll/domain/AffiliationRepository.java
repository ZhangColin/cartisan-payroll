package com.payroll.domain;

import com.cartisan.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface AffiliationRepository   extends BaseRepository<UnionAffiliation, Long> {
    Optional<UnionAffiliation> findByMemberIdAndEmployeeId(Long memberId, Long employeeId);

    List<UnionAffiliation> findByEmployeeId(Long employeeId);

    boolean existsByMemberIdAndEmployeeId(Long memberId, Long employeeId);

    void removeByMemberIdAndEmployeeId(Long memberId, Long employeeId);
}
