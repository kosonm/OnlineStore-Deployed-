package com.eshop.kosonm.account;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.eshop.kosonm.account.Account;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class AccountDao{
 
    private EntityManager entityManager;

    @Autowired
    public AccountDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }
 
    
    public Account findAccount(String userName) {
        Session session = this.entityManager.unwrap(Session.class);
        return session.find(Account.class, userName);
    }

    // quick solution, probably could do smth better
    // adds manager&employee acount 
    public void addAccounts(){
        
        Session session = this.entityManager.unwrap(Session.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(session.find(Account.class, "manager") == null){
        Account manager = new Account();
        manager.setActive(true);
        manager.setEncrytedPassword(bCryptPasswordEncoder.encode("123"));
        manager.setUserName("manager");
        manager.setUserRole("ROLE_MANAGER");
        session.save(manager);
        }

        if(session.find(Account.class, "employee") == null){
        Account employee = new Account();
        employee.setActive(true);
        employee.setEncrytedPassword(bCryptPasswordEncoder.encode("456"));
        employee.setUserName("employee");
        employee.setUserRole("ROLE_EMPLOYEE");
        session.save(employee);
        }



    }
 
}