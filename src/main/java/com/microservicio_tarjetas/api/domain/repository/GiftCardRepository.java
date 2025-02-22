package com.microservicio_tarjetas.api.domain.repository;

import com.microservicio_tarjetas.api.domain.model.GiftCard;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GiftCardRepository extends ReactiveCrudRepository<GiftCardEntity, Long> {
    Mono<GiftCardEntity> findById(Long id);
    Flux<GiftCardEntity> findAll();
    Mono<Void> deleteById(Long id);
    //Flux<GiftCard> findByUserId(String userId);
}
