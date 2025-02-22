package com.microservicio_tarjetas.api.application;

import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteGiftCardUseCase {

    private final GiftCardRepository repository;

    @Autowired
    public DeleteGiftCardUseCase(GiftCardRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> deleteGiftCard(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("GiftCard no encontrada con ID: " + id))) // Primero verificamos si existe
                .flatMap(existingGiftCard -> repository.delete(existingGiftCard)
                        .then(Mono.fromRunnable(() -> System.out.println("GiftCard eliminada con éxito")))
                )
                .doOnError(error -> System.out.println("Error al eliminar: " + error.getMessage())).then();
    }
}
