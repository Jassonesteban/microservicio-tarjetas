package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGiftCardByIdUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private EmitGiftCardUseCase emitGiftCardUseCase;

    @Test
    void testGetAllGiftCards() {

        GiftCardEntity giftCard1 = new GiftCardEntity(1L, "CODE123", null, "GiftCard 1", "Description 1", "Company A", null, null);
        GiftCardEntity giftCard2 = new GiftCardEntity(2L, "CODE456", null, "GiftCard 2", "Description 2", "Company B", null, null);

        when(repository.findAll()).thenReturn(Flux.just(giftCard1, giftCard2));

        Flux<GiftCardEntity> result = emitGiftCardUseCase.getAll();

        StepVerifier.create(result)
                .expectNext(giftCard1)
                .expectNext(giftCard2)
                .verifyComplete();
    }

    @Test
    void testGetAllGiftCards_Empty() {
        when(repository.findAll()).thenReturn(Flux.empty());

        Flux<GiftCardEntity> result = emitGiftCardUseCase.getAll();

        StepVerifier.create(result)
                .verifyComplete();
    }
}
