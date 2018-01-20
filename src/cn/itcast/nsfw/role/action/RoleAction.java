package cn.itcast.nsfw.role.action;


import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.constant.Constant;
import cn.itcast.core.exception.SysException;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.role.entity.RolePrivilegeId;
import cn.itcast.nsfw.role.service.RoleService;

@Controller("nsfw.roleAction")
@Scope(value = "prototype")
public class RoleAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private RoleService roleService;
	private List<Role> roleList;
	private Role role;
	private String[] privilegeIds;
	/**
	 * 列表页面
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String listUI(){
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		roleList = roleService.findObjects();
		return "listUI";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	public String addUI() {
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}

	/**
	 * 保存新增
	 * 
	 * @return
	 */
	public String add() {
		if(role!=null){
			if(privilegeIds!=null){
				HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
				for(int i = 0; i < privilegeIds.length; i++){
					set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
				}
				role.setRolePrivileges(set);
				roleService.save(role);
			}
		}
			
		return "list";
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	public String editUI() throws SysException{
		try{
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		if (role != null && role.getRoleId() != null) {
			role = roleService.findObjectById(role.getRoleId());
		if(role.getRolePrivileges()!=null){
			privilegeIds = new String[role.getRolePrivileges().size()];
			int i=0;
			for(RolePrivilege r : role.getRolePrivileges()){
				privilegeIds[i++] = r.getId().getCode();
			}
		}
		}
		return "editUI";
		}catch(Exception e){e.printStackTrace();}
		return null;
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 */
	public String edit() {
		try{
		if(role!=null){
			if(privilegeIds!=null&&privilegeIds.length>0){
				HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
				for(int i = 0; i < privilegeIds.length; i++){
					set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
				}
				role.setRolePrivileges(set);
				roleService.update(role);
			}
		}
		return "list";
		}catch(Exception e){e.printStackTrace();}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		if (role != null) {
			roleService.delete(role.getRoleId());
		}
		return "list";
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String deleteSelected() {
		if (selectedRow != null) {
			for (String id : selectedRow)
				roleService.delete(id);
		}
		return "list";
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

}
