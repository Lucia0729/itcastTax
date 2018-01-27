package cn.itcast.home.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

@Controller("sys.homeAction")
@Scope(value = "prototype")
public class HomeAction extends ActionSupport {
	@Resource
	private UserService userService;
	
	@Resource
	private InfoService infoService;

	@Resource
	private ComplainService complainService;
	private Complain comp;
	private String dept;
	private Map<String, Object> result_map;

	@Override
	public String execute() throws Exception {
		ActionContext.getContext().getContextMap().put("infoTypeMap" , Info.INFO_TYPE_MAP);
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "i");
		queryHelper1.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
		List<Info> infoList = infoService.findObjects(queryHelper1,1,5).getItems();
		ActionContext.getContext().getContextMap().put("infoList" , infoList);
		
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
		User user = (User) ActionContext.getContext().getSession().get(Constant.USER);
		queryHelper2.addCondition(" c.compName = ?", user.getName() );
		queryHelper2.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
		queryHelper2.addOrderByProperty("c.state", QueryHelper.ORDER_BY_DESC);
		List<Complain> complainList = complainService.findObjects(queryHelper2,1,6).getItems();
		ActionContext.getContext().getContextMap().put("complainList" , complainList);
		
		return "home";
	}

	public String complainAddUI() throws Exception {
		return "complainAddUI";
	}

	public String getUserJson2() {
		QueryHelper queryHelper = new QueryHelper(User.class, "u");
		if (dept != null) {
			queryHelper.addCondition("u.dept = ?", dept);
		}
		List<User> userList = userService.findObjects(queryHelper);
		result_map = new HashMap<>();
		result_map.put("msg", "success");
		result_map.put("userList", userList);
		return SUCCESS;

	}

	public void complainAdd() {
		try {
			if (comp != null) {
				comp.setState(Complain.COMPLAIN_STATE_UNDONE);
				comp.setCompTime(new Date());
				complainService.save(comp);
				HttpServletResponse reponse = ServletActionContext.getResponse();
				reponse.setContentType("text/html");
				ServletOutputStream out = reponse.getOutputStream();
				out.write("success".getBytes("utf-8"));
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String complainViewUI() throws Exception {
		return "complainViewUI";
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Complain getComp() {
		return comp;
	}

	public void setComp(Complain comp) {
		this.comp = comp;
	}

	public Map<String, Object> getResult_map() {
		return result_map;
	}

	public void setResult_map(Map<String, Object> result_map) {
		this.result_map = result_map;
	}

	

}
