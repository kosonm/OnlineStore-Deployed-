package com.eshop.kosonm.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.eshop.kosonm.customer.CustomerInfo;
import com.eshop.kosonm.product.ProductInfo;

import lombok.Data;

@Data
public class CartInfo {

    private int orderNum;

    private CustomerInfo customerInfo;

    private final List<CartLineInfo> cartLines = new ArrayList<>();

    private CartLineInfo findLineByCode(String code) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProductCode().equals(code)) {
                return line;
            }
        }
        return null;
    }
    /*
     * 
     * private Optional<CartLineInfo> findLineByCode(String code) { return
     * cartLines.stream()// .filter(line ->
     * line.getProductCode().equals(code)).findAny(); }
     * 
     * private CartLineInfo findLineByCode(String code){ CartLineInfo s =
     * this.cartLines.stream().filter(x ->
     * x.getProductCode().equals(code)).findAny().get();
     * 
     * if(s != null){ return s; } else return null; }
     */

    public void addProduct(ProductInfo productInfo, int quantity) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());

        line = new CartLineInfo();
        line.setQuantity(0);
        line.setProductInfo(productInfo);
        this.cartLines.add(line);
        
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }


    public void updateProduct(String code, int quantity) {
        CartLineInfo line = this.findLineByCode(code);
        if (quantity <= 0) {
            this.cartLines.remove(line);
        } else {
            Objects.requireNonNull(line).setQuantity(quantity);
        }
    }

    public void removeProduct(ProductInfo productInfo) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());
        this.cartLines.remove(line);
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    public boolean isValidCustomer() {
        return this.customerInfo.isValid();
    }

    public int getQuantityTotal() {
        return this.cartLines.stream().mapToInt(x -> x.getQuantity()).sum();
    }

    public double getAmountTotal() {
        return this.cartLines.stream().mapToDouble(CartLineInfo::getAmount).sum();
    }

    public void updateQuantity(CartInfo cartForm) {
        List<CartLineInfo> lines = cartForm.getCartLines();
        for (CartLineInfo line : lines) {
            this.updateProduct(line.getProductInfo().getCode(), line.getQuantity());
        }
    }

}