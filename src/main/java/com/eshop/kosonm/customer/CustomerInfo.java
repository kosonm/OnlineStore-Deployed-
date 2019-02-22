package com.eshop.kosonm.customer;

import com.eshop.kosonm.customer.CustomerForm;

import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class CustomerInfo {
 
    private String name;
    private String address;
    private String email;
    private String phone;
 
    private boolean valid;
 
    public CustomerInfo(CustomerForm customerForm) {
        this.name = customerForm.getName();
        this.address = customerForm.getAddress();
        this.email = customerForm.getEmail();
        this.phone = customerForm.getPhone();
        this.valid = customerForm.isValid();
    }
}