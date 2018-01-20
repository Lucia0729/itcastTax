package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.SerializeUtil;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

@Controller("sys.loginAction")
@Scope(value = "prototype")
public class LoginAction extends ActionSupport {
	Log log = LogFactory.getLog(getClass());
	private User user;
	private String loginResult;
	@Resource(name="userService")
	private UserService userService;
	public String toLoginUI() {
		return "loginUI";
	}

	public String login() {
		if(user!=null){
		if (StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())) {
			List<User> list = userService.findUserByAccountAndPass(user.getAccount(),user.getPassword());
			if(list!=null&&list.size()>0){
				User user = list.get(0);
				user.setUserRoles(userService.getUserRolesByUserId(user.getId()));
				ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
				Constant.jedis.set("userRole".getBytes(),SerializeUtil.serialize(user.getUserRoles()));
				log.info("用户名成为"+user.getName()+"的登录了系统");
				return "home";
			}else{
				loginResult = "用户名或密码错误";
			}
		}else{
			loginResult = "用户名或密码不能为空";
		}
		}else{
			loginResult = "请输入用户名密码";
		}
		return toLoginUI();
	}
	
	public String logout(){
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return toLoginUI();
	}
	
	public String toNoPermissionUI(){
		return "noPermissionUI";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}

}
