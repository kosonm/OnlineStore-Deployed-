package com.eshop.kosonm.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "orders", uniqueConstraints = { @UniqueConstraint(columnNames = "order_num") })
@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class Order implements Serializable {
 
 
    private static final long serialVersionUID = 8359668054019826940L;

    @Id
    @Column(name = "id", length = 50)
    private String id;
 
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
 
    @Column(name = "order_num", nullable = false)
    private int orderNum;
 
    @Column(name = "amount", nullable = false)
    private double amount;
 
    @Column(name = "customer_name", length = 255, nullable = false)
    private String customerName;
 
    @Column(name = "customer_address", length = 255, nullable = false)
    private String customerAddress;
 
    @Column(name = "customer_email", length = 128, nullable = false)
    private String customerEmail;
 
    @Column(name = "customer_phone", length = 128, nullable = false)
    private String customerPhone;
}