package cn.itcast.login.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.SerializeUtil;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

@Controller("sys.loginAction")
@Scope(value = "prototype")
public class LoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
				System.out.println(session.getAttribute(user.getId()));
				if(Constant.USER_SESSION.get(user.getId())!=null&&!session.equals(Constant.USER_SESSION.get(user.getId()))){
					userLoginHandle(user); 
				}
				user.setUserRoles(userService.getUserRolesByUserId(user.getId()));
				
				Constant.USER_SESSION.put(user.getId(), session);
				Constant.SESSIONID_USER.put(session.getId(), user.getAccount());
				session.setAttribute(user.getId(),session.getId());
				session.setAttribute(Constant.USER, user);
				session.setAttribute("isLogin", "false");
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
	
	private void userLoginHandle(User user) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(Constant.USER);
		HttpSession httpSession = Constant.USER_SESSION.get(user.getId());
		httpSession.setMaxInactiveInterval(0);
		Constant.USER_SESSION.remove(user.getId());
		Map<String,String> map = Constant.SESSIONID_USER;
		if(map.containsValue(user.getAccount())){
			 for (Entry<String, String> entry : map.entrySet()) {  
		            if(user.getAccount().equals(entry.getValue())){  
		            	Constant.SESSIONID_USER.remove(entry.getKey());
		            }  
		        }  
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
		session.removeAttribute(user.getId());
		
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
