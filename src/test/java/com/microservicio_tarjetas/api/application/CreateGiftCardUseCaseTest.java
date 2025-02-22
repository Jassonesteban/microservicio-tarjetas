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

public class CreateGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private CreateGiftCardUseCase createGiftCardUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateGiftCardSuccessfully() {
        GiftCardEntity giftCard = new GiftCardEntity(
                1L, "ABC1234567", new BigDecimal("100.00"), "Regalo",
                "Tarjeta de regalo", "Amazon", LocalDateTime.now(),
                LocalDateTime.now().plusMonths(6)
        );

        when(repository.save(any(GiftCardEntity.class))).thenReturn(Mono.just(giftCard));

        Mono<GiftCardEntity> result = createGiftCardUseCase.create(
                giftCard.getAmount(), giftCard.getName(), giftCard.getDescription(), giftCard.getCompany()
        );

        StepVerifier.create(result)
                .expectNextMatches(savedGiftCard -> savedGiftCard.getCode().length() == 10)
                .verifyComplete();

        verify(repository, times(1)).save(any(GiftCardEntity.class));
    }
}
