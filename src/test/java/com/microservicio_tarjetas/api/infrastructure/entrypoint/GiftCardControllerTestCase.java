package com.microservicio_tarjetas.api.infrastructure.entrypoint;

import com.microservicio_tarjetas.api.application.*;
import com.microservicio_tarjetas.api.domain.GiftCardService;
import com.microservicio_tarjetas.api.infrastructure.adapter.persistence.GiftCardEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(GiftCardController.class)
class GiftCardControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreateGiftCardUseCase createGiftCardUseCase;
    @MockBean
    private GetGiftCardUseCase getGiftCardUseCase;
    @MockBean
    private GiftCardService giftCardService;
    @MockBean
    private UpdateGiftCardUseCase updateGiftCardUseCase;
    @MockBean
    private DeleteGiftCardUseCase deleteGiftCardUseCase;
    @MockBean
    private EmitGiftCardUseCase emitGiftCardUseCase;

    private GiftCardEntity mockGiftCard;

    @BeforeEach
    void setUp() {
        mockGiftCard = new GiftCardEntity(
                1L, "ABC123", new BigDecimal("100.00"),
                "Regalo", "Tarjeta de regalo", "Amazon",
                LocalDateTime.now(), LocalDateTime.now().plusMonths(6)
        );
    }

    @Test
    void testCreateGiftCard() {
        Mockito.when(createGiftCardUseCase.create(any(), any(), any(), any()))
                .thenReturn(Mono.just(mockGiftCard));

        webTestClient.post().uri("/api/v1/tarjetas/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mockGiftCard)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Regalo")
                .jsonPath("$.amount").isEqualTo(100.00);
    }

    @Test
    void testGetById() {
        Mockito.when(getGiftCardUseCase.getById(1L))
                .thenReturn(Mono.just(mockGiftCard));

        webTestClient.get().uri("/api/v1/tarjetas/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.company").isEqualTo("Amazon");
    }

    @Test
    void testUpdateGiftCard() {
        GiftCardEntity updatedGiftCard = new GiftCardEntity(
                1L, "ABC123", new BigDecimal("200.00"),
                "Regalo Actualizado", "Nueva descripción", "Amazon",
                LocalDateTime.now(), LocalDateTime.now().plusMonths(6)
        );

        Mockito.when(updateGiftCardUseCase.updateGiftCard(eq(1L), any()))
                .thenReturn(Mono.just(updatedGiftCard));

        webTestClient.put().uri("/api/v1/tarjetas/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedGiftCard)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.amount").isEqualTo(200.00)
                .jsonPath("$.name").isEqualTo("Regalo Actualizado");
    }

    @Test
    void testDeleteGiftCard() {
        Mockito.when(deleteGiftCardUseCase.deleteGiftCard(1L))
                .thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/tarjetas/delete/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("GiftCard eliminada correctamente");
    }
}