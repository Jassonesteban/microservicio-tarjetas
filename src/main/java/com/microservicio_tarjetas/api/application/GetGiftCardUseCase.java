package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.model.GiftCard;
import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetGiftCardUseCase {

    private final GiftCardRepository repository;

    public GetGiftCardUseCase(GiftCardRepository repository) {
        this.repository = repository;
    }

    public Mono<GiftCardEntity> getById(Long id) {
        return repository.findById(id);
    }
}
