package com.microservicio_tarjetas.api.domain;

import com.microservicio_tarjetas.api.domain.model.GiftCard;
import com.microservicio_tarjetas.api.domain.model.GiftCardRequestDTO;
import com.microservicio_tarjetas.api.domain.repository.GiftCardRepository;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GiftCardService {

    private final GiftCardRepository giftCardRepository;

    public GiftCardService(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;
    }

    public Mono<GiftCardEntity> create(GiftCardEntity giftCard) {
        return giftCardRepository.save(giftCard);
    }

    public Mono<GiftCardEntity> getById(Long id) {
        return giftCardRepository.findById(id);
    }

    /*public Flux<GiftCardRequestDTO> getCardsByUserId(String userId) {
        return giftCardRepository.findByUserId(userId)
                .map(this::convertToDTO);
    }*/

    private GiftCardRequestDTO convertToDTO(GiftCard card) {
        return new GiftCardRequestDTO(
                card.getCode(),
                card.getAmount(),
                card.getName(),
                card.getDescription(),
                card.getCompany()
        );
    }

}
