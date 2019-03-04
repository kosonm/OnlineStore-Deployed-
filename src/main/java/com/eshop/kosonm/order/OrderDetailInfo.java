package com.eshop.kosonm.order;

import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class OrderDetailInfo {
    private String id;
 
    private String productCode;
    private String productName;
 
    private int quantity;
    private double price;
    private double amount;

    public OrderDetailInfo(){}

   //for jpa & hibernate
    public OrderDetailInfo(String id, String productCode,
                           String productName, int quantity, double price, double amount) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
}
}