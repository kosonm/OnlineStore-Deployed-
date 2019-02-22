package com.eshop.kosonm.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "products")
@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class Product implements Serializable {
 
    private static final long serialVersionUID = 7848111919923880227L;

    @Id
    @Column(name = "code", length = 20, nullable = false)
    private String code;
 
    @Column(name = "name", length = 255, nullable = false)
    private String name;
 
    @Column(name = "price", nullable = false)
    private double price;
 
    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
     
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;
}