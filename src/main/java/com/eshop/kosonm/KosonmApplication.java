package com.eshop.kosonm;

import com.eshop.kosonm.account.Account;
import com.eshop.kosonm.account.AccountDao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class KosonmApplication {

	public static void main(String[] args) {
		SpringApplication.run(KosonmApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(AccountDao accountDao) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return args -> {
			Account manager = new Account("manager", bCryptPasswordEncoder.encode("123"), "ROLE_MANAGER");
			Account employee = new Account("employee", bCryptPasswordEncoder.encode("456"), "ROLE_EMPLOYEE");
			accountDao.save(manager);
			accountDao.save(employee);

		};
	}

}
