package com.eshop.kosonm.account;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.eshop.kosonm.account.Account;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class AccountDao {

    private EntityManager entityManager;

    @Autowired
    public AccountDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findAccount(String userName) {
        Session session = this.entityManager.unwrap(Session.class);
        return session.find(Account.class, userName);
    }

    @Transactional
    public void save(Account newAccount) {
        Session session = this.entityManager.unwrap(Session.class);

        session.persist(newAccount);
        session.flush();
    }
}