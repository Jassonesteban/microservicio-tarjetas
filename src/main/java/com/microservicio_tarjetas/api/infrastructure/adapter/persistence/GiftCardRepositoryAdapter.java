package com.microservicio_tarjetas.api.infrastructure.adapter.persistence;

import com.microservicio_tarjetas.api.domain.model.GiftCard;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GiftCardRepositoryAdapter {

    private final GiftCardReactiveRepository repository;

    public GiftCardRepositoryAdapter(GiftCardReactiveRepository repository) {
        this.repository = repository;
    }

    public Mono<GiftCard> save(GiftCard giftCard) {
        return repository.save(mapToEntity(giftCard))
                .map(this::mapToDomain);
    }

    public Mono<GiftCard> findById(String id) {
        return repository.findById(id)
                .map(this::mapToDomain);
    }

    public Flux<GiftCard> findAll() {
        return repository.findAll()
                .map(this::mapToDomain);
    }

    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    private GiftCardEntity mapToEntity(GiftCard giftCard) {
        return new GiftCardEntity(
                giftCard.getId(),
                giftCard.getCode(),
                giftCard.getAmount(),
                giftCard.getName(),
                giftCard.getDescription(),
                giftCard.getCompany(),
                giftCard.getCreatedAt(),
                giftCard.getExpiresAt()
        );
    }

    private GiftCard mapToDomain(GiftCardEntity entity) {
        return new GiftCard(
                entity.getId(),
                entity.getCode(),
                entity.getAmount(),
                entity.getName(),
                entity.getDescription(),
                entity.getCompany(),
                entity.getCreatedAt(),
                entity.getExpiresAt()
        );
    }
}
