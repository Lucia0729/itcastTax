package cn.itcast.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService{
	@Resource
	private TestDao testDao;
	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("service say Hi");
	}
	public void save(Person person){
		testDao.save(person);
		int tem = 1/0;
	}
	@Override
	public Person findPerson(String id) {
		// TODO Auto-generated method stub
		save(new Person("test"));
		return testDao.findPerson(id);
	}

}
