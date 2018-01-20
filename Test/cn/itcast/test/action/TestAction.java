package cn.itcast.test.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.test.service.TestService;

@Controller("testAction")
public class TestAction extends ActionSupport{
	
	@Resource
	private TestService testService;
	public String execute(){
		testService.say();
		return SUCCESS;
	}
}
