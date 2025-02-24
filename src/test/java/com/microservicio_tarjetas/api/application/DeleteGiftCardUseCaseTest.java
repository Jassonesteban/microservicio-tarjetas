package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class DeleteGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private DeleteGiftCardUseCase deleteGiftCardUseCase;

    @Test
    void testDeleteGiftCard_Success() {
        Long giftCardId = 1L;
        GiftCardEntity existingGiftCard = new GiftCardEntity(giftCardId, "CODE123", null, null, null, null, null, null);

        when(repository.findById(giftCardId)).thenReturn(Mono.just(existingGiftCard));
        when(repository.delete(existingGiftCard)).thenReturn(Mono.empty());

        Mono<Void> result = deleteGiftCardUseCase.deleteGiftCard(giftCardId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).delete(existingGiftCard);
    }

    @Test
    void testDeleteGiftCard_NotFound() {
        Long giftCardId = 2L;
        when(repository.findById(giftCardId)).thenReturn(Mono.empty());
        Mono<Void> result = deleteGiftCardUseCase.deleteGiftCard(giftCardId);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("GiftCard no encontrada con ID: " + giftCardId))
                .verify();
    }
}
