package cn.itcast.nsfw.role.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public void deleteRolePrivilegeByRoleId(String roleId) {
		Session session = currentSession();
		Query query = session.createQuery(" delete from RolePrivilege where id.role.roleId = ?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

}
