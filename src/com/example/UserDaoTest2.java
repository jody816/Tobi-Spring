package com.example;

import javax.sql.DataSource;

import org.junit.Before;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/* 컨테이너 없는 DI 테스트
 * (스프링의 API에 의존하지 않음)
 * 
 * DI 컨테이너나 프레임워크는 DI를 편하게 적용하도록 도움을 줄 뿐,
 * 컨테이너가 DI를 가능하게 해주는 것은 아니다.
 * 
 * */
public class UserDaoTest2 {
	UserDao dao;
	
	@Before
	public void setUp() {
		dao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource(
				"jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger", true);
		dao.setDataSource(dataSource);
	}
}
