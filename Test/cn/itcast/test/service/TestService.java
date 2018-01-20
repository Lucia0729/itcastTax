package cn.itcast.test.service;

import cn.itcast.test.entity.Person;

public interface TestService {
	public abstract void say();
	public void save(Person person);
	public abstract Person findPerson(String id);
}
