package cn.itcast.nsfw.complain.action;

import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.entity.ComplainReply;
import cn.itcast.nsfw.complain.service.ComplainService;

@Controller("nsfw.complainAction")
public class ComplainAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(name = "complainService")
	private ComplainService complainService;

	private Complain complain;
	private ComplainReply reply;
	
	private String startTime;
	private String endTime;
	private String strTitle;
	private String strState;
	public String listUI() {
		try {
			ServletActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
			QueryHelper queryHelper = new QueryHelper(Complain.class, "c");

			if (StringUtils.isNotBlank(startTime)) {
				queryHelper.addCondition("c.compTime >= ?",
						DateUtils.parseDate(startTime, new String[] { "yyyy-MM-dd hh:mm:ss" }));
			}
			if (StringUtils.isNotBlank(endTime)) {
				queryHelper.addCondition("c.compTime <= ?",
						DateUtils.parseDate(endTime, new String[] { "yyyy-MM-dd hh:mm:ss" }));
			}
			if (complain != null) {
				if (StringUtils.isNotBlank(complain.getState())) {
					queryHelper.addCondition("c.state=?", complain.getState());
				}
				if (StringUtils.isNotBlank(complain.getCompTitle())) {
					complain.setCompTitle(URLDecoder.decode(complain.getCompTitle(), "utf-8"));
					queryHelper.addCondition("c.compTitle like ?", "%" + complain.getCompTitle() + "%");
				}
			}
			queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC);
			queryHelper.addOrderByProperty(" c.compTime", QueryHelper.ORDER_BY_ASC);
			pageResult = complainService.findObjects(queryHelper, getPageNo(), getPageSize());

		} catch (Exception e) {
		}
		return "listUI";

	}

	public String dealUI() {
		ServletActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(complain!=null){
			if(complain.getCompId()!=null){
				strTitle = complain.getCompTitle();
				strState = complain.getState();
				complain = complainService.findObjectById(complain.getCompId());
			}
		}
		return "dealUI";
	}
	public String deal(){
		if(complain!=null){
			Complain tem = complainService.findObjectById(complain.getCompId());
			if(!Complain.COMPLAIN_STATE_DONE.equals(complain.getState())){
				complain.setState(Complain.COMPLAIN_STATE_DONE);
			}
			if(reply!=null){
				reply.setCompId(complain);
				reply.setReplyTime(new Date());
				tem.getComplainReplies().add(reply);
				}
			complainService.update(tem);
		}
		return "list";
		
	}

	public String annualStatisticChartUI() {
		return "annualStatisticChartUI";
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	public ComplainReply getReply() {
		return reply;
	}

	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}

}
