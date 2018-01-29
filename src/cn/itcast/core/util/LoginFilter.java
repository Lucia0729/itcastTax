package cn.itcast.core.util;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute(Constant.USER);
		if (!uri.contains("/sys/login_")) {
			//在浏览器A登录进去之后，同一个账号在浏览器B登录了，再在A中访问会跳转到登录页面
			if(!Constant.SESSIONID_USER.containsKey(session.getId())){
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/sys/login_toLoginUI.action");
				
			}else{
			if (user != null) {
				PermissionCheck pc = null;
				WebApplicationContext application = WebApplicationContextUtils
						.getWebApplicationContext(session.getServletContext());
				pc = (PermissionCheck) application.getBean("permissionCheck");
				if (pc.isAccessible(user, "nsfw")) {
					filterChain.doFilter(request, response);
				} else {
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/sys/login_toNoPermissionUI.action");
				
				}

			} else {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/sys/login_toLoginUI.action");
				
			}
			}
		} else {
			String account = httpRequest.getParameter("user.account");
			String password = httpRequest.getParameter("user.password");
			String sessionId = session.getId();
			//同一浏览器访问同一网站的路径免登录
			if (Constant.SESSIONID_USER.containsKey(sessionId)) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/sys/home.action");
			}
			if (!Constant.SESSIONID_USER.containsKey(sessionId)&&Constant.SESSIONID_USER.containsValue(account)) {
				String islogin = httpRequest.getParameter("islogin");
				if(null!=islogin&&islogin.equals("true")){
					//发现该账号已登录，确定继续登录，正常执行操作
					filterChain.doFilter(request, response);
				}else{
					//发现该账号已登录，询问是否继续登录
					session.setAttribute("user.account", account);
					session.setAttribute("user.password", password);
					session.setAttribute("isLogin", "true");
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/sys/login_toLoginUI.action");
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
