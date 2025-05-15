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
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.country.dto.CountryDTO;
import com.gustavosass.orders.repository.CountryRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

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
    }

    @Test
    @DisplayName("Find country by ID successfully")
    void whenFindByIdThenReturnCountry() {
        when(countryRepository.findById(any(Long.class))).thenReturn(Optional.of(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        CountryDTO found = countryService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(country.getId());
        assertThat(found.getName()).isEqualTo(country.getName());
    }

    @Test
    @DisplayName("Throw exception when country ID not found")
    void whenFindByIdNotFoundThenThrowException() {
        when(countryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> countryService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("Country not found");
    }

    @Test
    @DisplayName("Find all countries successfully")
    void whenFindAllThenReturnCountryList() {
        when(countryRepository.findAll()).thenReturn(Arrays.asList(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        List<CountryDTO> countries = countryService.findAll();

        assertThat(countries).isNotEmpty();
        assertThat(countries).hasSize(1);
        assertThat(countries.get(0).getId()).isEqualTo(country.getId());
        assertThat(countries.get(0).getName()).isEqualTo(country.getName());
    }

    @Test
    @DisplayName("Check if country exists by name")
    void whenExistsByNameThenReturnTrue() {
        when(countryRepository.existsByName(any(String.class))).thenReturn(true);

        boolean exists = countryService.existsByName("Test Country");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Find country by name successfully")
    void whenFindByNameThenReturnCountry() {
        when(countryRepository.findByName(any(String.class))).thenReturn(Optional.of(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        Optional<CountryDTO> found = countryService.findByName("Test Country");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(country.getName());
    }

    @Test
    @DisplayName("Create country successfully")
    void whenCreateCountryThenSuccess() {
        when(countryRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(countryRepository.save(any(Country.class))).thenReturn(country);
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        CountryDTO created = countryService.create(countryDTO);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(country.getId());
        assertThat(created.getName()).isEqualTo(country.getName());
        verify(countryRepository).save(any(Country.class));
    }

    @Test
    @DisplayName("Throw exception when creating duplicate country")
    void whenCreateDuplicateCountryThenThrowException() {
        when(countryRepository.findByName(any(String.class))).thenReturn(Optional.of(country));

        Exception exception = assertThrows(DuplicateKeyException.class,
                () -> countryService.create(countryDTO));

        assertThat(exception.getMessage()).isEqualTo("Country already exist");
    }

    @Test
    @DisplayName("Update country successfully")
    void whenUpdateCountryThenSuccess() {
        Country updatedCountry = country;
        updatedCountry.setName("Updated Country");
        
        CountryDTO updatedCountryDTO = countryDTO;
        updatedCountryDTO.setName("Updated Country");

        when(countryRepository.findById(any(Long.class))).thenReturn(Optional.of(country));
        when(countryRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(updatedCountry);
        when(countryRepository.save(any(Country.class))).thenReturn(updatedCountry);
        when(countryMapper.toDTO(any(Country.class))).thenReturn(updatedCountryDTO);

        CountryDTO result = countryService.update(1L, updatedCountryDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Country");
        verify(countryRepository).save(any(Country.class));
    }

    @Test
    @DisplayName("Throw exception when updating country with null ID")
    void whenUpdateCountryWithNullIdThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> countryService.update(null, countryDTO));

        assertThat(exception.getMessage()).isEqualTo("ID/Country cannout be null");
    }

    @Test
    @DisplayName("Throw exception when updating country with null data")
    void whenUpdateCountryWithNullDataThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> countryService.update(1L, null));

        assertThat(exception.getMessage()).isEqualTo("ID/Country cannout be null");
    }

    @Test
    @DisplayName("Delete country successfully")
    void whenDeleteCountryThenSuccess() {
        when(countryRepository.findById(any(Long.class))).thenReturn(Optional.of(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        countryService.delete(1L);

        verify(countryRepository).deleteById(any(Long.class));
    }
} 