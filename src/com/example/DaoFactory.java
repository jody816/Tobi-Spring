package com.example;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
//		UserDao userDao = new UserDao(connectionMaker());
		UserDao userDao = new UserDao();
//		userDao.setConnectionMaker(connectionMaker());
//		userDao.setDataSource(dataSource());
		return userDao;
	}
	
//	@Bean
//	public DataSource dataSource() {
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		
//		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
//		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
//		dataSource.setUsername("scott");
//		dataSource.setPassword("tiger");
//		
//		return dataSource;
//	}
	
	// AccountDao, MessageDao
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
