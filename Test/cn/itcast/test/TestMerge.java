package cn.itcast.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

public class TestMerge {
	private ClassPathXmlApplicationContext context;
	@Before
	public void loadCtx(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	@Test
	public void testSpring(){
		TestService testService = (TestService) context.getBean("testService");
		testService.say();
	}
	@Test
	public void testHibernate(){
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(new Person("人员1"));
		tx.commit();
		session.close();
	}
	@Test
	public void testServiceAndDao(){
		TestService testService = (TestService) context.getBean("testService");
		testService.save(new Person("人员2"));
	}
	@Test
	public void testTransactionReadOnly() throws Exception{
		TestService testService = (TestService) context.getBean("testService");
		Person person = testService.findPerson("402888e45df92484015df924863a0000");
		System.out.println(person.getName());
	}
	@Test
	public void testTransactionRollBack() throws Exception{
		TestService testService = (TestService) context.getBean("testService");
		testService.save(new Person("人员3"));
		
	}
}
