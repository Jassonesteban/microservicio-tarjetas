package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CreateGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private CreateGiftCardUseCase createGiftCardUseCase;

    @Test
    void testCreateGiftCard() {
        BigDecimal amount = new BigDecimal(5000);
        String name = "PlayStation Plus";
        String description = "Membresía para PlayStation";
        String company = "Sony";
        String expectedCode = "PSN1234567";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMonths(6);

        GiftCardEntity expectedEntity = new GiftCardEntity(
                null,
                expectedCode,
                amount,
                name,
                description,
                company,
                createdAt,
                expiresAt
        );

        when(repository.save(Mockito.any(GiftCardEntity.class))).thenReturn(Mono.just(expectedEntity));

        Mono<GiftCardEntity> result = createGiftCardUseCase.create(amount, name, description, company);

        result.subscribe(giftCardEntity -> {
            assertNotNull(giftCardEntity);
            assertEquals(expectedEntity.getAmount(), giftCardEntity.getAmount());
            assertEquals(expectedEntity.getName(), giftCardEntity.getName());
            assertEquals(expectedEntity.getDescription(), giftCardEntity.getDescription());
            assertEquals(expectedEntity.getCompany(), giftCardEntity.getCompany());
            assertNotNull(giftCardEntity.getCode());
            assertFalse(giftCardEntity.getCode().isEmpty());
            assertEquals(expectedEntity.getExpiresAt(), giftCardEntity.getExpiresAt());
        });
    }
}
