package cn.itcast.core.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

@Component
public class PermissionCheck {
	@Resource
	private UserService userService;

	public boolean isAccessible(User user, String code) {
		if (Constant.jedis.get("userRole".getBytes()) != null) {
			List<UserRole> userRoles = (List<UserRole>) SerializeUtil
					.unserialize(Constant.jedis.get("userRole".getBytes()));
			if (userRoles == null)
				userRoles = userService.getUserRolesByUserId(user.getId());
			if (null != userRoles && userRoles.size() > 0) {
				for (UserRole userRole : userRoles) {
					Role role = userRole.getId().getRole();
					if (null != role.getRolePrivileges()) {
						for (RolePrivilege rp : role.getRolePrivileges()) {
							if (rp.getId().getCode().equals(code))
								return true;
						}
					}
				}
			}
		}
		return false;
	}

}
