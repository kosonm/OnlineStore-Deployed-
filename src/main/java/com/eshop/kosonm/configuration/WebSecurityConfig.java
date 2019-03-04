package com.eshop.kosonm.configuration;

import com.eshop.kosonm.account.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   /*
   Config class for Spring Security 
   */
   UserDetailsServiceImpl userDetailsService;

   @Autowired
   public WebSecurityConfig(UserDetailsServiceImpl userDetailsService){
      this.userDetailsService = userDetailsService;
   }
      
 
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
   }
 
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      /*
      Service finds User in the database, and setting passwordEncoder to bCryptPasswordEncoder
      */
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
 
   }
 
   @Override
   protected void configure(HttpSecurity http) throws Exception {
 
      http.csrf().disable();
      /*
      To access these pages you need to be logged in and have a employee/manager role
      or else it'll redirect you to /admin/login
      */
      http.authorizeRequests().antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")//
            .access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");
 
      /*
      Need to have manager role to access these
      */
      http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_MANAGER')");
      /*
      When you're logged in but try to access a page that requires a different role
      an 'AccessDeniedException' is thrown
      */
      http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
      /*
      Config for login form
      */
      http.authorizeRequests().and().formLogin()//
 
            .loginProcessingUrl("/j_spring_security_check")
            .loginPage("/admin/login")
            .defaultSuccessUrl("/admin/accountInfo")
            .failureUrl("/admin/login?error=true")
            .usernameParameter("userName")
            .passwordParameter("password")
 
            /*
            Config for logout - > you go to main page
            */
            .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");
 
   }
}
