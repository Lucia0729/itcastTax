package cn.itcast.core.constant;

import java.util.HashMap;
import java.util.Map;

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
	public static Map<String,String> PRIVILEGE_MAP;
	public static Jedis jedis;
	static{
		PRIVILEGE_MAP = new HashMap<String,String>();
		PRIVILEGE_MAP.put(PRIVILEGE_XZGL, "行政管理");
		PRIVILEGE_MAP.put(PRIVILEGE_HQFW, "后勤服务");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRIVILEGE_NSFW, "纳税服务");
		PRIVILEGE_MAP.put(PRIVILEGE_SPACE, "我的空间");
	}
	static{
		 jedis = new Jedis(HOST_IP,JEDIS_PORT);
	}
}
