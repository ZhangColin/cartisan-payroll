package com.payroll.domain;

import com.payroll.ServiceCharge;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceChargeServiceTest {

    @Test
    public void should_addServiceCharge_success() {
        // given
        final AffiliationRepository repository = mock(AffiliationRepository.class);
        ServiceChargeService serviceChargeService = new ServiceChargeService(repository);

        UnionAffiliation affiliation = new UnionAffiliation(1L, "");
        when(repository.findByMemberIdAndEmployeeId(anyLong(), anyLong())).thenReturn(Optional.of(affiliation));

        // when
        serviceChargeService.addServiceCharge(1L, 1L, LocalDate.of(2021, 8, 8), new BigDecimal("12.95") );

        ServiceCharge serviceCharge = affiliation.getServiceCharge(LocalDate.of(2021, 8, 8));

        // then
        assertThat(serviceCharge).isNotNull();
        assertThat(serviceCharge.getAmount()).isEqualTo(new BigDecimal("12.95"));
    }
}