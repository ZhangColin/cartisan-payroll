package com.payroll.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ServiceChargeServiceTest {
    private final AffiliationRepository repository;
    private final ServiceChargeService serviceChargeService;

    public ServiceChargeServiceTest() {
        repository = mock(AffiliationRepository.class);
        serviceChargeService = new ServiceChargeService(repository);
    }

    @Test
    public void should_addUnionAffiliation_success(){
        // when
        serviceChargeService.addUnionMember(1L, 1L, new BigDecimal("99.42"));

        // then
        verify(repository, atMostOnce()).save(argThat(unionAffiliation ->
                unionAffiliation.getEmployeeId().equals(1L)
                && unionAffiliation.getMemberId().equals(1L)
                && unionAffiliation.getDues().equals(new BigDecimal("99.42"))));
    }

    @Test
    public void should_removeUnionAffiliation_success(){
        // when
        serviceChargeService.addUnionMember(1L, 1L, new BigDecimal("99.42"));

        // then
        verify(repository, atMostOnce()).delete(argThat(unionAffiliation ->
                unionAffiliation.getEmployeeId().equals(1L)
                        && unionAffiliation.getMemberId().equals(1L)));
    }

    @Test
    public void should_addServiceCharge_success() {
        // given
        UnionAffiliation affiliation = new UnionAffiliation(1L, 1L, new BigDecimal("10"));
        when(repository.findByMemberIdAndEmployeeId(anyLong(), anyLong())).thenReturn(Optional.of(affiliation));

        // when
        serviceChargeService.addServiceCharge(1L, 1L, LocalDate.of(2021, 8, 8), new BigDecimal("12.95") );

        ServiceCharge serviceCharge = affiliation.getServiceCharge(LocalDate.of(2021, 8, 8));

        // then
        assertThat(serviceCharge).isNotNull();
        assertThat(serviceCharge.getAmount()).isEqualTo(new BigDecimal("12.95"));
    }
}