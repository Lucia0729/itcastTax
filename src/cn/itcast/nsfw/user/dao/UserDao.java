package cn.itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User>{

	List<User> findUserByAccountAndId(String id, String account);

	void saveUserRole(UserRole userRole);

	void deleteUserRoleByUserId(Serializable id);

	List<UserRole> getUserRolesByUserId(String id);

	List<User> findUserByAccountAndPass(String account, String password);

}
