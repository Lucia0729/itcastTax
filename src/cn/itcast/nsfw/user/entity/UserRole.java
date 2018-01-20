package cn.itcast.nsfw.user.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLE")
public class UserRole implements Serializable{
	@EmbeddedId
	private UserRoleId id;
	public UserRole() {
	}

	public UserRole(UserRoleId id) {
		this.id = id;
	}

	public UserRoleId getId() {
		return id;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}
}
