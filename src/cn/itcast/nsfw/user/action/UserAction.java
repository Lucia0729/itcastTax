package cn.itcast.nsfw.user.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

@Controller("nsfw.userAction")
@Scope(value = "prototype")
public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name="roleService")
	private RoleService roleService;
	private List<User> userList;
	private User user;
	
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	
	private String[] userRoleIds;

	/**
	 * 列表页面
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String listUI() throws Exception{
		try{
			
		userList = userService.findObjects();
		}catch(Exception e){
			throw new Exception("action 出现异常"+e.getMessage());
		}
		return "listUI";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	public String addUI() {
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		user = new User();
		return "addUI";
	}

	/**
	 * 保存新增
	 * 
	 * @return
	 */
	public String add() {
		try {
			if (user != null) {
				if (headImg != null) {
					// 1.保存头像到upload/user
					System.out.println("headImgContentType:" + headImgContentType);
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")
							+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					// 2.设置头像路径
					user.setHeadImg("user/" + fileName);
				}
				userService.saveUserAndRole(user, userRoleIds);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "list";
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	public String editUI() {
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		if (user != null && user.getId() != null) {
			user = userService.findObjectById(user.getId());
			//处理角色回显
			List<UserRole> list = userService.getUserRolesByUserId(user.getId());
			if(list != null && list.size() > 0){
				userRoleIds = new String[list.size()];
				for(int i = 0; i < list.size(); i++){
					userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
				}
			}}
		return "editUI";
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 */
	public String edit() {
		try {
			if (user != null) {
				if (headImg != null) {
					// 1.保存头像到upload/user
					System.out.println("headImgContentType:" + headImgContentType);
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")
							+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					// 2.设置头像路径
					user.setHeadImg("user/" + fileName);
				}
				userService.updateUserAndRole(user, userRoleIds);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		if (user != null) {
			userService.delete(user.getId());
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
				userService.delete(id);
		}
		return "list";
	}

	public void exportExcel(){
		try {
			userList = userService.findObjects();
			HttpServletResponse response = ServletActionContext.getResponse();
			if (userList != null && userList.size() != 0) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String("用户列表.xls".getBytes(), "ISO-8859-1"));
				ServletOutputStream outputStream = response.getOutputStream();
				userService.exportExcel(userList, outputStream);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String importExcel() {
		if (userExcel != null) {
			// 是否是excel
			if (userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				// 2:导入
				userService.importExcel(userExcel, userExcelFileName);
			}
		}
		return "list";
	}

	/**
	 * 数据校验，检查用户名不能重复
	 * 
	 * @return
	 */
	public void verifyAccount() {
		try {
			if (user != null && StringUtils.isNotBlank(user.getAccount())) {
				List<User> list = userService.findUserByAccountAndId(user.getId(), user.getAccount());
				String strResult = "true";
				if (list != null && list.size() > 0) {
					strResult = "false";
				}

				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(strResult.getBytes());
				outputStream.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

	public File getUserExcel() {
		return userExcel;
	}

	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}

	public String getUserExcelContentType() {
		return userExcelContentType;
	}

	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}

	public String getUserExcelFileName() {
		return userExcelFileName;
	}

	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}

	public String[] getUserRoleIds() {
		return userRoleIds;
	}

	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}

}
