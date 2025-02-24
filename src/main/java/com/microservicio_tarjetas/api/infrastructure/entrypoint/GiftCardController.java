package com.microservicio_tarjetas.api.infrastructure.entrypoint;

import com.microservicio_tarjetas.api.application.*;
import com.microservicio_tarjetas.api.domain.GiftCardService;
import com.microservicio_tarjetas.api.domain.model.GiftCard;
import com.microservicio_tarjetas.api.domain.model.GiftCardRequestDTO;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/tarjetas")
public class GiftCardController {

    private final CreateGiftCardUseCase createGiftCardUseCase;
    private final GetGiftCardUseCase getGiftCardUseCase;
    private final GiftCardService giftCardService;
    private final UpdateGiftCardUseCase updateGiftCardUseCase;
    private final DeleteGiftCardUseCase deleteGiftCardUseCase;
    private final EmitGiftCardUseCase emitGiftCardUseCase;

    @Autowired
    public GiftCardController(CreateGiftCardUseCase createGiftCardUseCase, GetGiftCardUseCase getGiftCardUseCase, GiftCardService giftCardService, UpdateGiftCardUseCase updateGiftCardUseCase, DeleteGiftCardUseCase deleteGiftCardUseCase, EmitGiftCardUseCase emitGiftCardUseCase) {
        this.createGiftCardUseCase = createGiftCardUseCase;
        this.getGiftCardUseCase = getGiftCardUseCase;
        this.giftCardService = giftCardService;
        this.updateGiftCardUseCase = updateGiftCardUseCase;
        this.deleteGiftCardUseCase = deleteGiftCardUseCase;
        this.emitGiftCardUseCase = emitGiftCardUseCase;
    }

    @GetMapping
    public Flux<GiftCardEntity> getAllGiftCards(){
        return emitGiftCardUseCase.getAll();
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<GiftCardEntity>> create(@RequestBody GiftCardEntity giftCard){
        return createGiftCardUseCase.create(
                giftCard.getAmount(),
                giftCard.getName(),
                giftCard.getDescription(),
                giftCard.getCompany()
        ).map(savegiftCard -> ResponseEntity.status(HttpStatus.CREATED).body(savegiftCard));
    }

    @GetMapping("/{id}")
    public Mono<GiftCardEntity> getById(@PathVariable Long id) {
        return getGiftCardUseCase.getById(id);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<GiftCardEntity>> updateGiftCard(@PathVariable Long id, @RequestBody GiftCardEntity giftCard){
        return updateGiftCardUseCase.updateGiftCard(id, giftCard)
                .map(updated -> ResponseEntity.ok().body(updated))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long id) {
        return deleteGiftCardUseCase.deleteGiftCard(id)
                .then(Mono.just(ResponseEntity.ok("GiftCard eliminada correctamente")))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())));
    }

}
