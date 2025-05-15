package com.gustavosass.orders.integration.viacep;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ViaCepClient {

    @Value("${via-cep.url}")
    private String url;

    private final WebClient webClient;

    public ViaCepClient() {
        this.webClient = WebClient.builder().build();
    }

    public ViaCepResponse getAddressByPostalCode(String postalCode) {
        try {
            ViaCepResponse viaCepResponse = webClient.get()
                    .uri(url + "/{cep}/json/", postalCode)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                        return Mono.error(new RuntimeException("CEP não encontrado"));
                    })
                    .onStatus(status -> status.is5xxServerError(), clientResponse -> {
                        return Mono.error(new RuntimeException("Erro no servidor do ViaCEP"));
                    })
                    .bodyToMono(ViaCepResponse.class)
                    .block();

            return viaCepResponse;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar o ViaCEP", e);
        }
    }
}
