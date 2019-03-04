package com.eshop.kosonm.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "accounts")
@Data // using Lombok library to eliminate 'boilerplate' code (getters&setters etc)
public class  Account implements Serializable {
 
    private static final long serialVersionUID = -6791390163061430546L;
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    @Id
    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;
 
    @Column(name = "encrypted_password", length = 128, nullable = false)
    private String encryptedPassword;
 
    @Column(name = "active", length = 1, nullable = false)
    private boolean active;
 
    @Column(name = "user_role", length = 20, nullable = false)
    private String userRole;

    public Account(String userName, String encryptedPassword, String userRole){
        this.active=true;
        this.userName=userName;
        this.encryptedPassword=encryptedPassword;
        this.userRole=userRole;
    }

    public Account(){}

    

}