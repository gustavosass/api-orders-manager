package com.gustavosass.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gustavosass.orders.enums.RoleEnum;
import com.gustavosass.orders.model.user.User;
import com.gustavosass.orders.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();
    }

    @Test
    @DisplayName("Busca um usuario pelo e-mail")
    void whenFindByEmailThenReturnUser() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        User found = userService.findByEmail(user.getEmail());

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Lançar excessão quando o e-mail não for encontrado")
    void whenFindByInvalidEmailThenThrowException() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.findByEmail("invalid@test.com"));
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    @DisplayName("Ao criar um usuário, deve retornar o usuário salvo")
    void whenCreateUserThenReturnSavedUser() {
        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(user.getPassword()))
                .thenReturn("encodedPassword");
        when(userRepository.save(user))
                .thenReturn(user);

        User created = userService.create(user);

        assertThat(created).isNotNull();
        verify(userRepository).save(user);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Lançar excessão quando tentar criar o usuario e o e-mail já existir")
    void whenCreateUserWithExistingEmailThenThrowException() {
        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.create(user));
        
        assertThat(exception.getMessage()).isEqualTo("Email already exists");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Listar todos os usuários")
    void whenFindAllThenReturnUserList() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> found = userService.findAll();

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Atualizar usuário com sucesso")
    void whenUpdateUserThenReturnUpdatedUser() {
        User updateUser = User.builder()
                .id(1L)
                .name("Updated Name")
                .email("test@test.com")
                .role(RoleEnum.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updateUser);

        User updated = userService.update(1L, updateUser);

        assertThat(updated.getName()).isEqualTo("Updated Name");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Retornar exceção ao atualizar usuário não existente")
    void whenUpdateNonExistentUserThenThrowException() {
        when(userRepository.findById(any()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> userService.update(1L, user));

        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    @DisplayName("Atualizar senha com sucesso")
    void whenUpdatePassworThenSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        userService.updatePassword(1L, "newPassword");

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("newPassword");
    }

    @Test
    @DisplayName("Deletar usuário com sucesso")
    void whenDeleteUserThenSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Lançar exceção ao tentar deletar usuário não existente")
    void whenDeleteNonExistentUserThenThrowException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> userService.delete(1L));
        
        assertThat(exception.getMessage()).isEqualTo("User not found");
        verify(userRepository, never()).deleteById(any());
    }
}