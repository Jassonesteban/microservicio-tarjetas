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

import static org.mockito.Mockito.*;

public class DeleteGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private DeleteGiftCardUseCase deleteGiftCardUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteGiftCardSuccessfully() {
        GiftCardEntity existingGiftCard = new GiftCardEntity();
        existingGiftCard.setId(1L);

        when(repository.findById(1L)).thenReturn(Mono.just(existingGiftCard));
        when(repository.delete(existingGiftCard)).thenReturn(Mono.empty());

        Mono<Void> result = deleteGiftCardUseCase.deleteGiftCard(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository, times(1)).delete(existingGiftCard);
    }
}
