package cn.itcast.core.util;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
		if (user.getUserRoles() != null) {
			List<UserRole> userRoles = user.getUserRoles();
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
