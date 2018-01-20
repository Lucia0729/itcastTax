package cn.itcast.home.action;




import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("sys.homeAction")
@Scope(value="prototype")
public class HomeAction extends ActionSupport{

	@Override
	public String execute() throws Exception {
		return "home";
	}
}
