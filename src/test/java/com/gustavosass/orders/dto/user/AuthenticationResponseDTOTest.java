package com.gustavosass.orders.dto.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.dto.AuthenticationResponseDTO;

class AuthenticationResponseDTOTest {

    @Test
    @DisplayName("Deve criar AuthenticationResponseDTO usando builder")
    void whenCreateUsingBuilderThenSuccess() {
        AuthenticationResponseDTO dto = AuthenticationResponseDTO.builder()
                .accessToken("test-access-token")
                .refreshToken("test-refresh-token")
                .expiresIn(3600L)
                .build();

        assertThat(dto.getAccessToken()).isEqualTo("test-access-token");
        assertThat(dto.getRefreshToken()).isEqualTo("test-refresh-token");
        assertThat(dto.getExpiresIn()).isEqualTo(3600L);
    }

    @Test
    @DisplayName("Deve criar AuthenticationResponseDTO usando construtor")
    void whenCreateUsingConstructorThenSuccess() {
        AuthenticationResponseDTO dto = new AuthenticationResponseDTO(
                "test-access-token",
                "test-refresh-token",
                3600L
        );

        assertThat(dto.getAccessToken()).isEqualTo("test-access-token");
        assertThat(dto.getRefreshToken()).isEqualTo("test-refresh-token");
        assertThat(dto.getExpiresIn()).isEqualTo(3600L);
    }

    @Test
    @DisplayName("Deve criar AuthenticationResponseDTO vazio e definir valores")
    void whenCreateEmptyAndSetValuesThenSuccess() {
        AuthenticationResponseDTO dto = new AuthenticationResponseDTO();
        dto.setAccessToken("test-access-token");
        dto.setRefreshToken("test-refresh-token");
        dto.setExpiresIn(3600L);

        assertThat(dto.getAccessToken()).isEqualTo("test-access-token");
        assertThat(dto.getRefreshToken()).isEqualTo("test-refresh-token");
        assertThat(dto.getExpiresIn()).isEqualTo(3600L);
    }

}