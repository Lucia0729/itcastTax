package cn.itcast.nsfw.role.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="ROLE_PRIVILEGE")
public class RolePrivilege implements Serializable{
	@EmbeddedId 
	private RolePrivilegeId id;

	
	public RolePrivilege() {
	}

	public RolePrivilege(RolePrivilegeId id) {
		this.id = id;
	}

	public RolePrivilegeId getId() {
		return id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}

}
