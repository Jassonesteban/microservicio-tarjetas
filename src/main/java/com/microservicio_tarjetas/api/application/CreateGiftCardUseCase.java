package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.model.GiftCard;
import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateGiftCardUseCase {

    @Autowired
    private final GiftCardRepository repository;

    public CreateGiftCardUseCase(GiftCardRepository repository) {
        this.repository = repository;
    }

    public Mono<GiftCardEntity> create(BigDecimal amount, String name, String description, String company) {
        String code = UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMonths(6);

        GiftCardEntity giftCardEntity = new GiftCardEntity(null, code, amount, name, description, company, now, expiration);

        return repository.save(giftCardEntity).doOnError(e -> System.out.println("Error al guardar " + e.getMessage()));
    }
}
