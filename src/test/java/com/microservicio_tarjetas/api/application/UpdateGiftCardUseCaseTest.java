package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private UpdateGiftCardUseCase updateGiftCardUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateGiftCardSuccessfully() {
        GiftCardEntity existingGiftCard = new GiftCardEntity(
                1L, "ABC1234567", new BigDecimal("100.00"), "Regalo",
                "Tarjeta de regalo", "Amazon", LocalDateTime.now(),
                LocalDateTime.now().plusMonths(6)
        );

        GiftCardEntity updatedGiftCard = new GiftCardEntity(
                1L, "ABC1234567", new BigDecimal("150.00"), "Regalo",
                "Tarjeta de regalo actualizada", "Amazon", LocalDateTime.now(),
                LocalDateTime.now().plusMonths(6)
        );

        when(repository.findById(1L)).thenReturn(Mono.just(existingGiftCard));
        when(repository.save(any(GiftCardEntity.class))).thenReturn(Mono.just(updatedGiftCard));

        Mono<GiftCardEntity> result = updateGiftCardUseCase.updateGiftCard(1L, updatedGiftCard );

        StepVerifier.create(result)
                .expectNextMatches(giftCard -> "Tarjeta de regalo actualizada".equals(giftCard.getDescription()))
                .verifyComplete();

        verify(repository, times(1)).save(any(GiftCardEntity.class));
    }
}
