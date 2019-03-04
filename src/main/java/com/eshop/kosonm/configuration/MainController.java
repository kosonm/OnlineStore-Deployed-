package com.eshop.kosonm.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
public class MainController {

   @RequestMapping("/403")
   public String accessDenied() {
      return "403";
   }

   @RequestMapping("/")
   public String home(Model model) {
      return "index";
   }
   
   @GetMapping("/admin/login")
   public String login(Model model) {
      return "login";
   }


}