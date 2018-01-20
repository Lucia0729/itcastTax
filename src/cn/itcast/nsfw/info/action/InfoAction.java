package cn.itcast.nsfw.info.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.exception.SysException;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

@Controller("nsfw.infoAction")
public class InfoAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InfoService infoService;
	private List<Info> infoList;
	private Info info;
	private String strTitle;

	/**
	 * 列表页面
	 * 
	 * @return
	 * @throws ServiceException 
	 * @throws Exception
	 */
	public String listUI() throws ServiceException {
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		QueryHelper queryHelper = new QueryHelper(Info.class,"i");
		if(info!=null){
			if(StringUtils.isNotBlank(info.getTitle())){
			queryHelper.addCondition(" i.title like ?", "%"+info.getTitle()+"%");
			}
			queryHelper.addOrderByProperty(" i.createTime ", QueryHelper.ORDER_BY_DESC);
		}
		
		infoList = infoService.findObjects(queryHelper);
		return "listUI";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	public String addUI() {
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		strTitle = info.getTitle();
		info = new Info();
		return "addUI";
	}

	/**
	 * 保存新增
	 * 
	 * @return
	 */
	public String add() {
		if (info != null) {
			info.setCreateTime(new Timestamp(new Date().getTime()));
			infoService.save(info);
		}
		return "list";
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	public String editUI() throws SysException {
		try {
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			if (info != null && info.getInfoId() != null) {
				strTitle = info.getTitle();
				info = infoService.findObjectById(info.getInfoId());

			}
			return "editUI";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 */
	public String edit() {
		try {
			if (info != null) {
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		if (info != null) {
			strTitle = info.getTitle();
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	
	public void publicInfo() throws IOException{
		if(info != null){
			Info tem  = infoService.findObjectById(info.getInfoId());
			tem.setState(info.getState());
			infoService.update(tem);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
//			ServletOutputStream outputStream = response.getOutputStream();
//			outputStream.write("更新状态成功".getBytes("utf-8"));
//			outputStream.close();
			PrintWriter writer = response.getWriter();
			writer.write("更新状态成功");
			writer.flush();
		}
		
		
	}

	public List<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	
}
