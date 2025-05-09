package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.dto.RegisterDTO;
import com.gustavosass.orders.dto.UserDTO;
import com.gustavosass.orders.enums.RoleEnum;
import com.gustavosass.orders.model.User;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    private User user;
    private RegisterDTO registerDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();

        registerDTO = new RegisterDTO(
                "Test User",
                "test@test.com",
                "password",
                RoleEnum.USER
        );

        userDTO = new UserDTO(
                1L,
                "Test User",
                "test@test.com",
                RoleEnum.USER
        );
    }

    @Test
    @DisplayName("Converter RegisterDTO para User")
    void whenToEntityFromRegisterDTOThenReturnUser() {
        User mappedUser = userMapper.toEntity(registerDTO);
        
        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getName()).isEqualTo(registerDTO.getName());
        assertThat(mappedUser.getEmail()).isEqualTo(registerDTO.getEmail());
        assertThat(mappedUser.getPassword()).isEqualTo(registerDTO.getPassword());
        assertThat(mappedUser.getRole()).isEqualTo(registerDTO.getRole());
    }

    @Test
    @DisplayName("Converter UserDTO para User")
    void whenToEntityFromUserDTOThenReturnUser() {
        User mappedUser = userMapper.toEntity(userDTO);
        
        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getId()).isEqualTo(userDTO.getId());
        assertThat(mappedUser.getName()).isEqualTo(userDTO.getName());
        assertThat(mappedUser.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(mappedUser.getRole()).isEqualTo(userDTO.getRole());
    }

    @Test
    @DisplayName("Converter User para UserDTO")
    void whenToDTOThenReturnUserDTO() {
        UserDTO mappedDTO = userMapper.toDTO(user);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(user.getId());
        assertThat(mappedDTO.getName()).isEqualTo(user.getName());
        assertThat(mappedDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(mappedDTO.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("Converter User para RegisterDTO")
    void whenToRegisterDTOThenReturnRegisterDTO() {
        RegisterDTO mappedDTO = userMapper.toRegisterDTO(user);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getName()).isEqualTo(user.getName());
        assertThat(mappedDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(mappedDTO.getPassword()).isEqualTo(user.getPassword());
        assertThat(mappedDTO.getRole()).isEqualTo(user.getRole());
    }
}