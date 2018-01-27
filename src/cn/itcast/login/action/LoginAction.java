package cn.itcast.login.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
				HttpSession session = ServletActionContext.getRequest().getSession();
				if(Constant.jedis.get(user.getId())!=null&&!session.getId().equals(Constant.jedis.get(user.getId()))){
					tologout(user);
				}
				Constant.jedis.set(user.getId(),session.getId());
				user.setUserRoles(userService.getUserRolesByUserId(user.getId()));
				session.setAttribute(Constant.USER, user);
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
	
	private void tologout(User user) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(Constant.USER);
		Constant.jedis.del(user.getId());
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("您已在其他地点登录".getBytes());
			outputStream.close();
			response.sendRedirect(request.getContextPath()+"/sys/toLoginUI.action");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String logout(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute(Constant.USER);
//		System.out.println(user.getId()+" "+user.getName());
		/*Set<String> set = Constant.jedis.keys("*");
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext())
			log.info(  iterator.next());*/
		Constant.jedis.del(user.getId());
		
		session.removeAttribute(Constant.USER);
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
