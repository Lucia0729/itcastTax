package cn.itcast.nsfw.role.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ROLE")
public class Role implements Serializable{
	private String roleId;
	private String name;
	private String state;
	private Set<RolePrivilege> rloePrivileges; 
	
	public static String ROLE_STATE_VALID = "1";//有效
	public static String ROLE_STATE_INVALID = "0";//无效
	
	public Role() {
		super();
	}
	
	public Role(String roleId, String name, String state, Set<RolePrivilege> rloePrivileges) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.state = state;
		this.rloePrivileges = rloePrivileges;
	}

	public Role(String roleId) {
		this.roleId = roleId;
	}

	@Id
	@GenericGenerator(name="roleid",strategy="uuid")
	@GeneratedValue(generator="roleid")
	@Column(name="roleId",length=32)
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Column(name="name",nullable=true,length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="state",length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@OneToMany(mappedBy="id.role",fetch=FetchType.EAGER,cascade={CascadeType.ALL}) 
	public Set<RolePrivilege> getRolePrivileges() {
		return rloePrivileges;
	}
	public void setRolePrivileges(Set<RolePrivilege> rloePrivileges) {
		this.rloePrivileges = rloePrivileges;
	}
	   
}
