package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateGiftCardUseCase {
    private final GiftCardRepository repository;

    public UpdateGiftCardUseCase(GiftCardRepository repository) {
        this.repository = repository;
    }

    public Mono<GiftCardEntity> updateGiftCard(Long id, GiftCardEntity updatedGiftCard) {
        return repository.findById(id)
                .flatMap(existingGiftCard -> {
                    if (updatedGiftCard.getAmount() != null) {
                        existingGiftCard.setAmount(updatedGiftCard.getAmount());
                    }
                    if (updatedGiftCard.getName() != null) {
                        existingGiftCard.setName(updatedGiftCard.getName());
                    }
                    if (updatedGiftCard.getDescription() != null) {
                        existingGiftCard.setDescription(updatedGiftCard.getDescription());
                    }
                    if (updatedGiftCard.getCompany() != null) {
                        existingGiftCard.setCompany(updatedGiftCard.getCompany());
                    }

                    return repository.save(existingGiftCard);
                })
                .doOnError(e -> System.out.println("Error al actualizar: " + e.getMessage()));
    }
}
