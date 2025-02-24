package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private UpdateGiftCardUseCase updateGiftCardUseCase;

    @Test
    void testUpdateGiftCard_Success() {
        Long id = 1L;
        GiftCardEntity existingGiftCard = new GiftCardEntity(id, "CODE123", new BigDecimal("100.00"), "Old Name", "Old Description", "Old Company", null, null);
        GiftCardEntity updatedGiftCard = new GiftCardEntity(null, null, new BigDecimal("200.00"), "New Name", "New Description", "New Company", null, null);
        GiftCardEntity expectedUpdatedGiftCard = new GiftCardEntity(id, "CODE123", new BigDecimal("200.00"), "New Name", "New Description", "New Company", null, null);

        when(repository.findById(id)).thenReturn(Mono.just(existingGiftCard));
        when(repository.save(any(GiftCardEntity.class))).thenReturn(Mono.just(expectedUpdatedGiftCard));

        Mono<GiftCardEntity> result = updateGiftCardUseCase.updateGiftCard(id, updatedGiftCard);

        StepVerifier.create(result)
                .expectNextMatches(giftCard ->
                        giftCard.getAmount().equals(new BigDecimal("200.00")) &&
                                giftCard.getName().equals("New Name") &&
                                giftCard.getDescription().equals("New Description") &&
                                giftCard.getCompany().equals("New Company"))
                .verifyComplete();

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(GiftCardEntity.class));
    }

    @Test
    void testUpdateGiftCard_NotFound() {
        Long id = 1L;
        GiftCardEntity updatedGiftCard = new GiftCardEntity(null, null, new BigDecimal("200.00"), "New Name", "New Description", "New Company", null, null);
        when(repository.findById(id)).thenReturn(Mono.empty());
        Mono<GiftCardEntity> result = updateGiftCardUseCase.updateGiftCard(id, updatedGiftCard);
        StepVerifier.create(result)
                .verifyComplete();
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any(GiftCardEntity.class));
    }
}
