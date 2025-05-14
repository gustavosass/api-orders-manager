package com.gustavosass.orders.model.address;

import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.client.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String number;
    private String district;
    private String complement;
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToOne(mappedBy = "address")
    private Client client;
}
