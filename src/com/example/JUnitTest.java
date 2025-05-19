package com.example;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/* not()
 * 뒤에 나오는 결과를 부정해는 매처
 * 
 * is()
 * equals() 비교를 해서 같으면 성공, is(not())은 반대로 같지 않아야 성공!
 * 
 * sameInstance()
 * 실제로 같은 오브젝인지를 비교 (동일성 비교 매처)
 * 
 * 현재 메서드는 테스트 메서드가 실행될 때마다
 * 스태틱 변수인 testObject에 저장해둔 오브젝트와 다른
 * 새로운 테스트 오브젝트가 만들어졌음을 확인할 수 있다.
 * (Set 사용 전)
 * 
 * ---
 * 
 * hasItem()
 * 컬렉션의 원소인지를 검사하는 매처
 * 
 * assertThat()
 * 매처와 비교할 대상인 첫 번째 파라미터에 Boolean 타입의 결과가 나오는 조건문을 넣음
 * 
 * assertTrue()
 * 조건문을 받아서 그 결과가 true인지 false인지 확인하도록 만들어진 검증용 메서드
 * 
 * either()
 * 뒤에 이어서 나오는 or()와 함께 두 개의 매처의 결과를 OR 조건으로 비교해줌
 * 두 가지 매처 중에서 하나만 true로 나와도 성공임
 * -> assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
 * 
 * nullValue()
 * 오브젝트가 null인지 확인해주는 메서드
 * */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/junit.xml")
public class JUnitTest {
	@Autowired
	ApplicationContext context;
	
	static Set<JUnitTest> testObjects = new HashSet<>();
	static ApplicationContext contextObject = null;
	
	@Test
	public void test1() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
		// 테스트 컨텍스트가 매번 주입해주는 앱 컨텍스트는
		// 항상 같은 오브젝트인지 테스트로 확인해봄
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}
	
	@Test
	public void test2() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
	
	@Test
	public void test3() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
		assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
		contextObject = this.context;
	}
}
