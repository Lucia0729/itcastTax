package cn.itcast.core.constant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import redis.clients.jedis.Jedis;

public class Constant {
	public static final String USER = "SYS_USER";
	private static String HOST_IP="127.0.0.1";
	private static int JEDIS_PORT=6379;
	private static String PRIVILEGE_XZGL="xzgl";
	private static String PRIVILEGE_HQFW="hqfw";
	private static String PRIVILEGE_ZXXX="zxxx";
	private static String PRIVILEGE_NSFW="nsfw";
	private static String PRIVILEGE_SPACE="spaces";
	//用户和Session绑定关系 
	public static final Map<String, HttpSession> USER_SESSION=new HashMap<String, HttpSession>();
	//seeionId和用户的绑定关系
	public static final Map<String, String> SESSIONID_USER=new HashMap<String, String>();
	public static Map<String,String> PRIVILEGE_MAP;
	static{
		PRIVILEGE_MAP = new HashMap<String,String>();
		PRIVILEGE_MAP.put(PRIVILEGE_XZGL, "行政管理");
		PRIVILEGE_MAP.put(PRIVILEGE_HQFW, "后勤服务");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRIVILEGE_NSFW, "纳税服务");
		PRIVILEGE_MAP.put(PRIVILEGE_SPACE, "我的空间");
	}
	
}
