package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EmitGiftCardUseCase {
    private final GiftCardRepository repository;


    public EmitGiftCardUseCase(GiftCardRepository repository) {
        this.repository = repository;
    }

    public Flux<GiftCardEntity> getAll(){
        return repository.findAll();
    }
}
