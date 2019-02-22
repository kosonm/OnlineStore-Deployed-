package com.eshop.kosonm.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eshop.kosonm.product.Product;

import lombok.Data;

import javax.persistence.ForeignKey;
import javax.persistence.Id;

@Entity
@Table(name = "order_details")
@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
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
 
}