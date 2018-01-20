package cn.itcast.test.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;

@Repository("testDao")
public class TestDaoImpl extends HibernateDaoSupport implements TestDao{
	@Resource
	public void setSessionFactory0(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	} 
	@Override
	public void save(Person person) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(person);
	}
	@Override
	public Person findPerson(String id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Person.class, id);
	}

}
