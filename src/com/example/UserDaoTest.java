package com.example;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/* JUnit 은 특정한 테스트 메서드의 실행 순서를 보장해주지 않는다.
 * 
 * 테스트하기 전에 테스트 실행에 문제가 되지 않는 상태를 만들어주는 편이 더 나을 것이다!
 * 
 * if 문장의 기능 -> JUnit이 제공해주는 assertThat이라는 스태틱 메서드를 이용한다. (assertThat)
 * 
 * @Before
 * 테스트를 실행할 때마다 반복되는 준비 작업을 별도의 메서드에 넣게 해주고,
 * 이를 매번 테스트 메서드를 실행하기 전에 먼저 실행시켜 주는 기능
 * 이는, 테스트 메서드에서 직접 호출하지 않기 때문에 서로 주고받을 정보나
 * 오브젝트가 있다면 인스턴스 변수를 이용해야 한다.(private UserDao dao)
 * 
 * 테스트 메서드의 일부에서만 공통적으로 사용되는 코드가 있다면,
 * @Before를 사용하기보단, 일반적인 메서드 추출 방법을 써서 분리하고,
 * 테스트 메서드에서 직접 호출해 사용하도록 만드는 편이 낫다. 또는 별토의 테스트 클래스로 만드는 방법도 ㅇㅇ
 * 하지만, 메서드 하나당 한 번씩 객체를 생성해주기 때문에, 테스트 메서드 수만큼 반복되어 생성됨
 * 
 * @BeforeClass 스태틱 메서드
 * 이 메서드에서 애플리케이션 컨텍스트를 만들어 스태틱 변수에 저장해두고 테스트 메서드에서 사용하게 할 수 있다.
 * but, 스프링이 직접 제공하는 애플리케이션 컨텍스트 테스트 지원 기능을 사용하는 것이 더 편리함.
 * 
 * ---
 * 
 * @DirtiesContext
 * 테스트 메서드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을
 * 테스트 컨텍스트 프레임워크에게 알려준다.
 * 클래스뿐만 아니라, 메서드 레벨에서도 적용이 가능함. 하나의 메서드에서만 상태 변경이 필요하다면,
 * 메서드 레벨에 붙여주는 것이 낫다.
 * 
 * UserDao 빈의 의존 관계를 강제로 변경하게 되면,
 * 한 번 변경하면 나머지 모든 테스트를 수행하는 동안 변경된 애플리케이션 컨텍스트가 계속 사용될 것이다.
 * (바람직하지 않음)
 * 
 * 그래서, 위 어노테이션을 사용하면, 스프링 컨텍스트 프레임워크에게 해당 클래스의 테스트에서
 * 애플리케이션 컨텍스트의 상태를 변경한다는 것을 알려준다.
 * 테스트 컨텍스트는 이 어노테이션이 붙은 테스트 클래스에는
 * 애플리케이션 컨텍스트 공유를 허용하지 않는다.
 * -> 테스트 메서드를 수행하고 나면 매번 새로운 앱 컨텍스트를 만들어서
 * 		 다음 테스트가 사용하게 해준다. (영향을 안 받음)
 * 
 * ---
 * 
 * @RunWith
 * 스프링 테스트 컨텍스트 프레임워크 적용
 * JUnit이 테스트 실행 시 Spring 컨테이너를 함께 로딩해서,
 * 테스트 클래스 내에서 스프링 빈, 의존성 주입(@Autowired 등)을 사용할 수 있게 해줌.
 * 
 * 193페이지 "테스트를 위한 별도의 DI 설정"
 * 
 * */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@DirtiesContext
public class UserDaoTest {
//	@Autowired
//	private ApplicationContext context;
	
	@Autowired
	private UserDao dao;
	// 픽스처
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		DataSource dataSource = new SingleConnectionDataSource(
				"jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger", true);
		dao.setDataSource(dataSource);
//		this.dao = this.context.getBean("userDao", UserDao.class);
		this.user1 = new User("gyumee", "박성철", "springno1");
		this.user2 = new User("eeee", "이길원", "springno2");
		this.user3 = new User("diododo", "박범진", "springno3");
		
//		System.out.println(this.context);
//		System.out.println(this);
	}
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//					"applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		User user = new User();		
//		user.setId("gyumee");
//		user.setName("박성철");
//		user.setPassword("springno1");
//		
//		dao.add(user);
//		
//		User user2 = dao.get(user.getId());
//
//		assertThat(user2.getName(), is(user.getName()));
//		assertThat(user2.getPassword(), is(user.getPassword()));
		
//		User user1 = new User("gyumee", "박성철", "springno1");
//		User user2 = new User("eeee", "이길원", "springno2");
		
		this.dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		this.dao.add(this.user1);
		this.dao.add(this.user2);
		assertThat(this.dao.getCount(), is(2));
		
		User userGet1 = this.dao.get(user1.getId());
		assertThat(userGet1.getName(), is(user1.getName()));
		assertThat(userGet1.getPassword(), is(user1.getPassword()));
		
		User userGet2 = this.dao.get(user2.getId());
		assertThat(userGet2.getName(), is(user2.getName()));
		assertThat(userGet2.getPassword(), is(user2.getPassword()));
	}
	
	@Test 
	public void count() throws SQLException {
//		ApplicationContext context = new GenericXmlApplicationContext(
//				"applicationContext.xml");
//		
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		User user1 = new User("gyumee", "박성철", "springno1");
//		User user2 = new User("eeee", "이길원", "springno2");
//		User user3 = new User("diododo", "박범진", "springno3");
		
		this.dao.deleteAll();
		assertThat(this.dao.getCount(), is(0));
		
		this.dao.add(this.user1);
		assertThat(this.dao.getCount(), is(1));
		
		this.dao.add(this.user2);
		assertThat(this.dao.getCount(), is(2));
		
		this.dao.add(this.user3);
		assertThat(this.dao.getCount(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
//		ApplicationContext context = new GenericXmlApplicationContext(
//				"applicationContext.xml");
		
//		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknow_id");
	}
	
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		
////		ApplicationContext context =
////				new AnnotationConfigApplicationContext(DaoFactory.class);
////		
////		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		User user = new User();		
//		user.setId("whiteship");
//		user.setName("백기선");
//		user.setPassword("married");
//		
//		dao.add(user);
//		
//		System.out.println(user.getId() + " 등록 성공");
//		
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());
//		System.out.println(user2.getPassword());
//		
//		System.out.println(user2.getId() + " 조회 성공");
//	}
}
