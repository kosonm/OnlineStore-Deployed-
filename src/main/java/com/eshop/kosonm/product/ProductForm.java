package com.eshop.kosonm.product;

import com.eshop.kosonm.product.Product;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class ProductForm {
    private String code;
    private String name;
    private double price;
 
    private boolean newProduct = false;
 
    // Upload file.
    private MultipartFile fileData;
 
    public ProductForm() {
        this.newProduct= true;
    }
    public ProductForm(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }
 
}