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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import lombok.Data;


@Entity
@Table(name = "products")
@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class Product implements Serializable {
 
    private static final long serialVersionUID = 7848111919923880227L;

    @Id
    @Column(name = "code", length = 20, nullable = false)
    @Length(max = 6, message = "Code cannot be longer than 6 characters")
    private String code;
 
    @Column(name = "name", length = 20, nullable = false)
    @Length(max = 10, message = "Name cannot be longer than 10 characters")
    private String name;

    @Positive(message = "Price must be a positive")
    @Max(value = 99999, message = "Price cannot be more than 99999")
    @Min(value = 1,message = "Price must be at least 1 [j]")
    @Column(name = "price", nullable = false)
    private double price;
 
    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE)
    private byte[] image;
     
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;
}