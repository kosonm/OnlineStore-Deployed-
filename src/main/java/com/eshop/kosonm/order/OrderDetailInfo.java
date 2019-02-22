package com.eshop.kosonm.order;

import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class OrderDetailInfo {
    private String id;
 
    private String productCode;
    private String productName;
 
    private int quanity;
    private double price;
    private double amount;

   //for jpa & hibernate
    public OrderDetailInfo(String id, String productCode, 
            String productName, int quanity, double price, double amount) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.quanity = quanity;
        this.price = price;
        this.amount = amount;
}
}