package com.rectus29.beertender.spring.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import javax.annotation.PostConstruct;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/04/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Configuration
@Import(value = {
		SecurityConfig.class,
		SchedulerConfig.class
})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.rectus29.beertender"})
public class BeerTenderMainConfig /*implements TransactionManagementConfigurer */ {
	private static final Logger LOG = LoggerFactory.getLogger(BeerTenderMainConfig.class);
	@Autowired
	private Environment env;

	@PostConstruct
	public void initApp() {
		LOG.debug("Looking for Spring profiles...");
		if (env.getActiveProfiles().length == 0) {
			LOG.info("No Spring profile configured, running with default configuration.");
		} else {
			for (String profile : env.getActiveProfiles()) {
				LOG.info("Detected Spring profile: {}", profile);
			}
		}
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}

	@Bean
	public ApplicationContextAware contextApplicationContextProvider() {
		return new com.rectus29.beertender.spring.ApplicationContextProvider();
	}

	@Bean
	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL55Dialect");
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "beerTenderProperties")
	public Properties getBeertenderProperties() {
		Properties prop = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("beertender.properties")) {
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file not found in the classpath");
			}
		} catch (Exception ex) {
			LOG.error("Error while application properties read", ex);
		}
		return prop;
	}

	@Bean
	@Autowired
	public ComboPooledDataSource dataSource(Properties beerTenderProperties) throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(beerTenderProperties.getProperty("dataBase.driver"));
		dataSource.setJdbcUrl(beerTenderProperties.getProperty("dataBase.url"));
		dataSource.setUser(beerTenderProperties.getProperty("dataBase.user"));
		dataSource.setPassword(beerTenderProperties.getProperty("dataBase.password"));
		dataSource.setAutoCommitOnClose(true);
		dataSource.setIdleConnectionTestPeriod(15);
		dataSource.setMaxIdleTime(15);
		return dataSource;
	}

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(ComboPooledDataSource dataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);

		return entityManagerFactory;
	}


	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(Properties beerTenderProperties) {
		try {
			LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
			localSessionFactoryBean.setDataSource(dataSource(beerTenderProperties));
			localSessionFactoryBean.setPackagesToScan("com.rectus29.beertender.entities");
			localSessionFactoryBean.setHibernateProperties(hibernateProperties());
			return localSessionFactoryBean;
		} catch (PropertyVetoException e) {
			LOG.error("Error while session factory init", e);
			return null;
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		try {
			HibernateTransactionManager txManager = new HibernateTransactionManager();
			txManager.setSessionFactory(sessionFactory(getBeertenderProperties()).getObject());
			txManager.setDataSource(dataSource(getBeertenderProperties()));
			return txManager;
		} catch (PropertyVetoException e) {
			LOG.error("Error while transaction manager init", e);
			return null;
		}
	}

	/*@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}*/

	@Bean
	public JavaMailSenderImpl javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("127.0.0.1");
//		mailSender.setUsername("127.0.0.1");
//		mailSender.setPassword("127.0.0.1");
		mailSender.setPort(25);
		mailSender.setDefaultEncoding("UTF-8");
		return mailSender;
	}

	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
		FreeMarkerConfigurationFactoryBean freeMarkerConfiguration = new FreeMarkerConfigurationFactoryBean();
		freeMarkerConfiguration.setTemplateLoaderPath("classpath:/mailTemplate");
		freeMarkerConfiguration.setPreferFileSystemAccess(false);
		return freeMarkerConfiguration;
	}


	Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.show_sql", "false");
				setProperty("hibernate.format_sql", "false");
				setProperty("hibernate.hbm2ddl.auto", "update");
				setProperty("hibernate.c3p0.min_size", "5");
				setProperty("hibernate.c3p0.max_size", "20");
				setProperty("hibernate.c3p0.timeout", "300");
				setProperty("hibernate.c3p0.max_statements", "50");
				setProperty("connection.provider_class", "org.hibernate.c3p0.internal.C3P0ConnectionProvider");
				setProperty("hibernate.dialect.storage_engine", "innodb");
				setProperty("hibernate.search.default.directory_provider", "filesystem");
				setProperty("hibernate.search.default.indexBase", "/lucene/indexes");
				setProperty("hibernate.search.default.optimizer.operation_limit.max", "500");
			}
		};
	}

}
