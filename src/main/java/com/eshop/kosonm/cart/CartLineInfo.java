package com.eshop.kosonm.cart;

import com.eshop.kosonm.product.ProductInfo;

import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class CartLineInfo {
  
    private ProductInfo productInfo;
    private int quantity;
  
    public CartLineInfo() {
        this.quantity = 0;
    }
  
    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
     
}