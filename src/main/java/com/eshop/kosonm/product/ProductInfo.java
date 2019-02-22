package com.eshop.kosonm.product;

import com.eshop.kosonm.product.Product;

import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class ProductInfo {
    private String code;
    private String name;
    private double price;

    public ProductInfo() {

    }

    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    // for jpa hibernate
    public ProductInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
}