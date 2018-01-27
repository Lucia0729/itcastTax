package cn.itcast.core.util;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
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
		if(!uri.contains("/sys/login_")){
			HttpSession session = httpRequest.getSession();
			User user = (User) session.getAttribute(Constant.USER);
			if(user!=null){
				PermissionCheck pc = null;
				WebApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
				pc= (PermissionCheck)application.getBean("permissionCheck");
				if(pc.isAccessible(user,"nsfw")){
					filterChain.doFilter(request, response);
				}else{
					httpResponse.sendRedirect(httpRequest.getContextPath()+"/sys/login_toNoPermissionUI.action");
//					httpRequest.getRequestDispatcher(httpRequest.getContextPath()+"/sys/login_toNoPermissionUI.action").forward(httpRequest, httpResponse);
				}
				
			}else{
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/sys/login_toLoginUI.action");
//				httpRequest.getRequestDispatcher(httpRequest.getContextPath()+"/sys/login_toNoPermissionUI.action").forward(httpRequest, httpResponse);
				}
		}else{
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
