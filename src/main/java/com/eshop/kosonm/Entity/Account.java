package com.eshop.kosonm.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
 
    private static final long serialVersionUID = -6791390163061430546L;
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
 
    @Id
    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;
 
    @Column(name = "encryted_password", length = 128, nullable = false)
    private String encrytedPassword;
 
    @Column(name = "active", length = 1, nullable = false)
    private boolean active;
 
    @Column(name = "user_role", length = 20, nullable = false)
    private String userRole;
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEncrytedPassword() {
        return encrytedPassword;
    }
 
    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
 
    public boolean isActive() {
        return active;
    }
 
    public void setActive(boolean active) {
        this.active = active;
    }
 
    public String getUserRole() {
        return userRole;
    }
 
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
 
}