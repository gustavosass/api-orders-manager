package com.gustavosass.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.mapper.StateMapper;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.repository.StateRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @Mock
    private StateMapper stateMapper;

    @Mock
    private CountryService countryService;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private StateService stateService;

    private State state;
    private StateDTO stateDTO;
    private Country country;
    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    }

    @Test
    @DisplayName("Find state by ID successfully")
    void whenFindByIdThenReturnState() {
        when(stateRepository.findById(any(Long.class))).thenReturn(Optional.of(state));
        when(stateMapper.toDTO(any(State.class))).thenReturn(stateDTO);

        StateDTO found = stateService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(state.getId());
        assertThat(found.getName()).isEqualTo(state.getName());
        assertThat(found.getInitials()).isEqualTo(state.getInitials());
    }

    @Test
    @DisplayName("Throw exception when state ID not found")
    void whenFindByIdNotFoundThenThrowException() {
        when(stateRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> stateService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("State not found");
    }

    @Test
    @DisplayName("Find all states successfully")
    void whenFindAllThenReturnStateList() {
        when(stateRepository.findAll()).thenReturn(Arrays.asList(state));
        when(stateMapper.toDTO(any(State.class))).thenReturn(stateDTO);

        List<StateDTO> states = stateService.findAll();

        assertThat(states).isNotEmpty();
        assertThat(states).hasSize(1);
        assertThat(states.get(0).getId()).isEqualTo(state.getId());
        assertThat(states.get(0).getName()).isEqualTo(state.getName());
        assertThat(states.get(0).getInitials()).isEqualTo(state.getInitials());
    }

    @Test
    @DisplayName("Find state by ID and country ID successfully")
    void whenFindByIdAndCountryIdThenReturnState() {
        when(stateRepository.findByIdAndCountryId(any(Long.class), any(Long.class))).thenReturn(Optional.of(state));
        when(stateMapper.toDTO(any(State.class))).thenReturn(stateDTO);

        StateDTO found = stateService.findByIdAndCountryId(1L, 1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(state.getId());
        assertThat(found.getName()).isEqualTo(state.getName());
        assertThat(found.getInitials()).isEqualTo(state.getInitials());
    }

    @Test
    @DisplayName("Throw exception when state ID and country ID not found")
    void whenFindByIdAndCountryIdNotFoundThenThrowException() {
        when(stateRepository.findByIdAndCountryId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> stateService.findByIdAndCountryId(1L, 1L));

        assertThat(exception.getMessage()).isEqualTo("State not found");
    }

    @Test
    @DisplayName("Create state successfully")
    void whenCreateStateThenSuccess() {
        when(countryService.findById(any(Long.class))).thenReturn(countryDTO);
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(country);
        when(stateMapper.toEntity(any(StateDTO.class))).thenReturn(state);
        when(stateRepository.findByNameAndCountryId(any(String.class), any(Long.class))).thenReturn(Optional.empty());
        when(stateRepository.save(any(State.class))).thenReturn(state);
        when(stateMapper.toDTO(any(State.class))).thenReturn(stateDTO);

        StateDTO created = stateService.create(stateDTO);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(state.getId());
        assertThat(created.getName()).isEqualTo(state.getName());
        assertThat(created.getInitials()).isEqualTo(state.getInitials());
        verify(stateRepository).save(any(State.class));
    }

    @Test
    @DisplayName("Throw exception when creating duplicate state")
    void whenCreateDuplicateStateThenThrowException() {
        when(stateMapper.toEntity(any(StateDTO.class))).thenReturn(state);
        when(stateRepository.findByNameAndCountryId(any(String.class), any(Long.class))).thenReturn(Optional.of(state));

        Exception exception = assertThrows(DuplicateKeyException.class,
                () -> stateService.create(stateDTO));

        assertThat(exception.getMessage()).isEqualTo("State already exist");
    }

    @Test
    @DisplayName("Throw exception when creating state with null data")
    void whenCreateStateWithNullDataThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> stateService.create(null));

        assertThat(exception.getMessage()).isEqualTo("State cannot be null");
    }

    @Test
    @DisplayName("Update state successfully")
    void whenUpdateStateThenSuccess() {
        State updatedState = state;
        updatedState.setName("Updated State");
        
        StateDTO updatedStateDTO = stateDTO;
        updatedStateDTO.setName("Updated State");

        when(stateRepository.findById(any(Long.class))).thenReturn(Optional.of(state));
        when(countryService.findById(any(Long.class))).thenReturn(countryDTO);
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(country);
        when(stateMapper.toEntity(any(StateDTO.class))).thenReturn(updatedState);
        when(stateRepository.findByNameAndCountryId(any(String.class), any(Long.class))).thenReturn(Optional.empty());
        when(stateMapper.toDTO(any(State.class))).thenReturn(updatedStateDTO);

        StateDTO result = stateService.update(1L, updatedStateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated State");
    }

    @Test
    @DisplayName("Throw exception when updating state with null data")
    void whenUpdateStateWithNullDataThenThrowException() {

        when(stateRepository.findById(any(Long.class))).thenReturn(Optional.of(state));
        
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> stateService.update(1L, null));

        assertThat(exception.getMessage()).isEqualTo("State cannot be null");
    }

    @Test
    @DisplayName("Delete state successfully")
    void whenDeleteStateThenSuccess() {
        when(stateRepository.findById(any(Long.class))).thenReturn(Optional.of(state));
        when(stateMapper.toDTO(any(State.class))).thenReturn(stateDTO);

        stateService.delete(1L);

        verify(stateRepository).deleteById(any(Long.class));
    }
} 