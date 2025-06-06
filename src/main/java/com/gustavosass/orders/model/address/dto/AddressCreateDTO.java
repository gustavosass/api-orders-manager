package com.gustavosass.orders.model.address.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressCreateDTO {

   private String street;
   private String number;
   private String district;
   private String complement;
   private String postalCode;

   @NotNull(message = "City ID cannot be null")
   private Long cityId;

   @NotNull(message = "State ID cannot be null")
   private Long stateId;

   @NotNull(message = "CountryID cannot be null")
   private Long countryId;
}
