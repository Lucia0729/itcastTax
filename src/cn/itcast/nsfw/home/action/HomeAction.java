package cn.itcast.nsfw.home.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.core.action.BaseAction;


@Controller("nsfw.homeAction")
@Scope(value="prototype")
public class HomeAction extends BaseAction{
	public String frame(){
		return "frame";
	}
	public String top(){
		return "top";
	}
	public String left(){
		return "left";
	}

}
