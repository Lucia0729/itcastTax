package cn.itcast.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.ValueGenerationType;

@Entity
@Table(name="PERSON")
public class Person implements Serializable{
private String id;
private String name;
public Person() {
	super();
}
public Person(String name) {
	super();
	this.name = name;
}
@Id
@GenericGenerator(strategy="uuid.hex", name = "p_uuid")
@GeneratedValue(generator="p_uuid")
@Column(name="id",length=32)
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
@Column(name="NAME",length=20,nullable=true)
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}
