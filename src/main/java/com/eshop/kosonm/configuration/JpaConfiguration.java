package com.eshop.kosonm.configuration;

import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // this class has beans methods annotated with @Bean
@EnableTransactionManagement // enable annotation-driven transaction management capability
@ComponentScan({ "com.eshop.kosonm.configuration.JpaConfiguration" }) // where to look for spring managed beans/classes
@PropertySource(value = { "classpath:application.properties" }) // declares a set of properties(file in application
// classpath) in Spring run-time Environment
public class JpaConfiguration {

    /*
     * Creates a LocalContainerEntityManagerFactoryBean, which is same as the XML
     * based configuration: needs a dataSource and properties.
     * 
     * transactionManager() provides transaction support
     */

    Environment env;

    @Autowired
    public JpaConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws URISyntaxException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.eshop.kosonm.cart", "com.eshop.kosonm.customer",
                "com.eshop.kosonm.order", "com.eshop.kosonm.product", "com.eshop.kosonm.account" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    private Properties hibernateProperties() {
        Properties p = new Properties();
        p.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        p.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.show-sql"));
        p.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
        p.put("current_session_context_class",
                env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        return p;
    }

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        // 1

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        // 1

        /*
         * 
         * // 2 URI jdbUri = new URI(System.getenv("JAWSDB_URL")); String username =
         * jdbUri.getUserInfo().split(":")[0]; String password =
         * jdbUri.getUserInfo().split(":")[1]; String port =
         * String.valueOf(jdbUri.getPort()); String jdbUrl = "jdbc:mysql://" +
         * jdbUri.getHost() + ":" + port + jdbUri.getPath();
         * 
         * DriverManagerDataSource dataSource = new DriverManagerDataSource();
         * dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName")
         * ); dataSource.setUsername(username); dataSource.setPassword(password);
         * dataSource.setUrl(jdbUrl); // 2
         */

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
