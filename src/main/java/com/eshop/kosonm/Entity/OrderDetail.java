package com.eshop.kosonm.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;
import javax.persistence.Id;

@Entity
@Table(name = "order_details")
public class OrderDetail implements Serializable {
 
 
    private static final long serialVersionUID = 6155204466272437279L;

    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "order_detail_ord_fk"))
    private Order order;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "order_detail_prod_fk"))
    private Product product;
 
    @Column(name = "quanity", nullable = false)
    private int quanity;
 
    @Column(name = "price", nullable = false)
    private double price;
 
    @Column(name = "amount", nullable = false)
    private double amount;
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public Order getOrder() {
        return order;
    }
 
    public void setOrder(Order order) {
        this.order = order;
    }
 
    public Product getProduct() {
        return product;
    }
 
    public void setProduct(Product product) {
        this.product = product;
    }
 
    public int getQuanity() {
        return quanity;
    }
 
    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
 
}