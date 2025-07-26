package com.gustavosass.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gustavosass.orders.model.Customer;
import com.gustavosass.orders.model.Address;

@Service
public class CustomerAddressService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    public Address addAddressToCustomer(Long customerId, Address address) {
        Customer customer = customerService.findById(customerId);
        if (customer == null)
            return null;
        Address savedAddress = addressService.create(address);
        customer.setAddress(savedAddress);
        customerService.update(customer.getId(), customer);
        return savedAddress;
    }

    public Address updateCustomerAddress(Long customerId, Address address) {
        Customer customer = customerService.findById(customerId);
        if (customer == null)
            return null;
        Address existing = customer.getAddress();
        if (existing != null) {
            address.setId(existing.getId());
            Address updated = addressService.update(existing.getId(), address);
            customer.setAddress(updated);
            customerService.update(customer.getId(), customer);
            return updated;
        } else {
            return addAddressToCustomer(customerId, address);
        }
    }

    public Address getCustomerAddress(Long customerId) {
        Customer customer = customerService.findById(customerId);
        return customer != null ? customer.getAddress() : null;
    }

    public void removeCustomerAddress(Long customerId) {
        Customer customer = customerService.findById(customerId);
        if (customer != null && customer.getAddress() != null) {
            Long addressId = customer.getAddress().getId();
            customer.setAddress(null);
            customerService.update(customer.getId(), customer);
            addressService.delete(addressId);
        }
    }
}
