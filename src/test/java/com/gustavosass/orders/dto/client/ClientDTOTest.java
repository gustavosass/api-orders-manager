package com.gustavosass.orders.dto.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootTest
class ClientDTOTest {

    private Validator validator;
    private ClientDTO clientDTO;
    private City city;
    private CityDTO cityDTO;
    private State state;
    private StateDTO stateDTO;
    private Country country;
    private CountryDTO countryDTO;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        country = Country.builder()
                .id(1L)
                .name("Test Country")
                .build();
                
        countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();

        state = State.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .country(country)
                .build();
                
        stateDTO = StateDTO.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .countryDTO(countryDTO)
                .build();

        city = City.builder()
                .id(1L)
                .name("Test City")
                .state(state)
                .build();
                
        cityDTO = CityDTO.builder()
                .id(1L)
                .name("Test City")
                .stateDTO(stateDTO)
                .build();

        addressDTO = AddressDTO.builder()
                .cityDTO(cityDTO)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();

        clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .addressDTO(addressDTO)
                .build();
    }

    @Test
    @DisplayName("Should validate when all fields are valid")
    void whenValidDTOThenNoViolations() {
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
        assertThat(violations).isEmpty();
    }

    @Nested
    @DisplayName("Name validation tests")
    class NameValidationTests {
        @Test
        @DisplayName("Should fail validation when name is empty")
        void whenEmptyNameThenViolation() {
            clientDTO.setName("");
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Nome é obrigatório");
        }

        @Test
        @DisplayName("Should fail validation when name is null")
        void whenNullNameThenViolation() {
            clientDTO.setName(null);
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Nome é obrigatório");
        }
    }

    @Nested
    @DisplayName("Email validation tests")
    class EmailValidationTests {
        @Test
        @DisplayName("Should fail validation when email is invalid")
        void whenInvalidEmailThenViolation() {
            clientDTO.setEmail("invalid-email");
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Email inválido");
        }

        @Test
        @DisplayName("Should fail validation when email is empty")
        void whenEmptyEmailThenViolation() {
            clientDTO.setEmail("");
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Email é obrigatório");
        }

        @Test
        @DisplayName("Should fail validation when email is null")
        void whenNullEmailThenViolation() {
            clientDTO.setEmail(null);
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Email é obrigatório");
        }
    }

    @Nested
    @DisplayName("Document validation tests")
    class DocumentValidationTests {
        @Test
        @DisplayName("Should fail validation when document is empty")
        void whenEmptyDocumentThenViolation() {
            clientDTO.setDocument("");
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Documento é obrigatório");
        }

        @Test
        @DisplayName("Should fail validation when document is null")
        void whenNullDocumentThenViolation() {
            clientDTO.setDocument(null);
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("Documento é obrigatório");
        }
    }

    @Nested
    @DisplayName("Optional fields validation tests")
    class OptionalFieldsValidationTests {
        @Test
        @DisplayName("Should not fail validation when complement is empty")
        void whenEmptyComplementThenNoViolation() {
            clientDTO.getAddressDTO().setComplement("");
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should not fail validation when phone is empty")
        void whenEmptyPhoneThenNoViolation() {
            clientDTO.setPhone("");
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should not fail validation when birthDate is null")
        void whenNullBirthDateThenNoViolation() {
            clientDTO.setBirthDate(null);
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            
            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Builder pattern tests")
    class BuilderPatternTests {
        @Test
        @DisplayName("Should create ClientDTO using builder pattern")
        void whenCreateUsingBuilderThenSuccess() {
            ClientDTO dto = ClientDTO.builder()
                    .id(1L)
                    .name("Test Client")
                    .email("test@test.com")
                    .birthDate(new Date())
                    .phone("123456789")
                    .document("12345678900")
                    .addressDTO(addressDTO)
                    .build();

            assertThat(dto).isNotNull();
            assertThat(dto.getName()).isEqualTo("Test Client");
            assertThat(dto.getEmail()).isEqualTo("test@test.com");
            assertThat(dto.getDocument()).isEqualTo("12345678900");
            assertThat(dto.getAddressDTO()).isNotNull();
        }
    }
} 