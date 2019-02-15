package com.eshop.kosonm.Configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /*
    Configuration for messages shown in app (f.ex. error message for login input like "NotEmpty" for name)
    */
 
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
  
}