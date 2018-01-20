package cn.itcast.test.dao;

import cn.itcast.test.entity.Person;

public interface TestDao {

	public void save(Person person);

	public Person findPerson(String id);

}
