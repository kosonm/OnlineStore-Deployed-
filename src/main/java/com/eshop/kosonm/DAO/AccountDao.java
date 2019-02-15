package com.eshop.kosonm.DAO;

import javax.transaction.Transactional;

import com.eshop.kosonm.Entity.Account;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class AccountDao{
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public Account findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Account.class, userName);
    }

    // quick solution, probably could do smth better
    // adds manager&employee acount 
    public void addAccounts(){
        Session session = sessionFactory.getCurrentSession();
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