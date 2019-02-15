package com.eshop.kosonm.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // this class has beans methods annotated with @Bean
@EnableTransactionManagement // enable annotation-driven transaction management capability
@ComponentScan({ "com.eshop.kosonm.Configuration.HibernateConfig" }) // where to look for spring managed beans/classes
@PropertySource(value = { "classpath:application.properties" }) // declares a set of properties(file in application
// classpath) in Spring run-time Environment
public class HibernateConfiguration {

    /*
     * Creates a LocalSessionFactoryBean, which is same as the XML based
     * configuration: needs a dataSource and hibernate properties.
     * 
     * Once the SessionFactory is created, it will be injected into Bean method
     * transactionManager which may eventually provide transaction support for the
     * sessions created by this sessionFactory.
     * 
     */

    @Autowired
    Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws URISyntaxException {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setHibernateProperties(hibernateProperties());
        sf.setPackagesToScan(new String[] { "com.eshop.kosonm.Entity" });
        return sf;
    }

    private Properties hibernateProperties() {
        Properties p = new Properties();
        p.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        p.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.show-sql"));
        // p.put("hibernate.format_sql",
        // env.getRequiredProperty("hibernate.format_sql"));
        p.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
        p.put("current_session_context_class",
                env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        return p;
    }

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        /*

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
          dataSource.setDriverClassName(env.getRequiredProperty(
          "spring.datasource.driver-class-name"));
          dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
          dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
          dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
         */

        URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
        String username = jdbUri.getUserInfo().split(":")[0];
        String password = jdbUri.getUserInfo().split(":")[1];
        String port = String.valueOf(jdbUri.getPort());
        String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(jdbUrl);

        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sf) throws URISyntaxException {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sf);

        return manager;

    }

}
