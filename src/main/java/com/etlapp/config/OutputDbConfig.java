package com.etlapp.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for adding support for outbound jdbc (when extracting to the app's
 * JDBC and for setting transformed entities into DB)
 * @author Leo Guercio
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef	= "stagingEntityManagerFactory",
	transactionManagerRef = "stagingTransactionManager",
	basePackages = { "com.etlapp.dataintegration.repository" }
)
public class OutputDbConfig {
	
	@Primary
	@Bean(name = "stagingDataSource")
	@ConfigurationProperties(prefix = "staging.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean stagingEntityManagerFactory(EntityManagerFactoryBuilder builder,
																			  @Qualifier("stagingDataSource")
																	          DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.etlapp.entities")
				.persistenceUnit("output")
				.build();
	}
	
	@Primary
	@Bean(name = "stagingTransactionManager")
	public PlatformTransactionManager stagingTransactionManager(@Qualifier("stagingEntityManagerFactory")
	                                                            EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}

