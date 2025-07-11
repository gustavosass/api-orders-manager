package com.gustavosass.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.orders.enums.RoleEnum;
import com.gustavosass.orders.model.User;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Test User")
                .email("test@test.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();
    }

    @Test
    @DisplayName("Salvar um usuário com sucesso")
    void whenSaveUserThenReturnSavedUser() {
        User savedUser = userRepository.save(user);
        
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Encontrar usuário por email")
    void whenFindByEmailThenReturnUser() {
        userRepository.save(user);
        
        var foundUser = userRepository.findByEmail(user.getEmail());
        
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Retornar vazio quando buscar email não existente")
    void whenFindByNonExistingEmailThenReturnEmpty() {
        var foundUser = userRepository.findByEmail("nonexisting@test.com");
        
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Retornar true quando email existir")
    void whenEmailExistsThenReturnTrue() {
        userRepository.save(user);
        
        boolean exists = userRepository.existsByEmail(user.getEmail());
        
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Retornar false quando email não existir")
    void whenEmailDoesNotExistThenReturnFalse() {
        boolean exists = userRepository.existsByEmail("nonexisting@test.com");
        
        assertThat(exists).isFalse();
    }
}
