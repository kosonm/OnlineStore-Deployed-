package com.eshop.kosonm.customer;

import com.eshop.kosonm.customer.CustomerInfo;
import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class CustomerForm {
 
    private String name;
    private String address;
    private String email;
    private String phone;
 
    private boolean valid;
 
    public CustomerForm(CustomerInfo customerInfo) {
        if (customerInfo != null) {
            this.name = customerInfo.getName();
            this.address = customerInfo.getAddress();
            this.email = customerInfo.getEmail();
            this.phone = customerInfo.getPhone();
        }
    }
 
}