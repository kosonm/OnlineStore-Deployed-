package com.eshop.kosonm.controller;

import com.eshop.kosonm.account.AccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
public class MainController {

   private AccountDao accountDao;

   @Autowired
   public MainController(AccountDao accountDao) {
      this.accountDao = accountDao;
   }

   @RequestMapping("/403")
   public String accessDenied() {
      return "403";
   }

   @RequestMapping("/")
   public String home(Model model) {
      accountDao.addAccounts();
      return "index";
   }

}