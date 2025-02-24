package com.microservicio_tarjetas.api.domain;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCardServiceTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @InjectMocks
    private GiftCardService giftCardService;

    private GiftCardEntity giftCardEntity;

    @BeforeEach
    void setUp() {
        giftCardEntity = new GiftCardEntity();
        giftCardEntity.setId(1L);
        giftCardEntity.setCode("GIFT123");
        giftCardEntity.setAmount(BigDecimal.valueOf(100.00));
        giftCardEntity.setName("Regalo");
        giftCardEntity.setDescription("Tarjeta de regalo");
        giftCardEntity.setCompany("Amazon");
        giftCardEntity.setCreatedAt(LocalDateTime.now());
        giftCardEntity.setExpiresAt(LocalDateTime.now().plusDays(30));
    }

    @Test
    void testCreateGiftCard() {
        when(giftCardRepository.save(any(GiftCardEntity.class))).thenReturn(Mono.just(giftCardEntity));
        Mono<GiftCardEntity> result = giftCardService.create(giftCardEntity);
        StepVerifier.create(result)
                .expectNext(giftCardEntity)
                .verifyComplete();
        verify(giftCardRepository, times(1)).save(any(GiftCardEntity.class));
    }

    @Test
    void testGetByIdGiftCard() {
        when(giftCardRepository.findById(1L)).thenReturn(Mono.just(giftCardEntity));
        Mono<GiftCardEntity> result = giftCardService.getById(1L);
        StepVerifier.create(result)
                .expectNext(giftCardEntity)
                .verifyComplete();
        verify(giftCardRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdGiftCard_NotFound() {
        when(giftCardRepository.findById(1L)).thenReturn(Mono.empty());
        Mono<GiftCardEntity> result = giftCardService.getById(1L);
        StepVerifier.create(result)
                .verifyComplete();
        verify(giftCardRepository, times(1)).findById(1L);
    }
}
