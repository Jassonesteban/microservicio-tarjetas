package com.microservicio_tarjetas.api.infrastructure.adapter.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardReactiveRepository extends ReactiveCrudRepository<GiftCardEntity, String> {
}
