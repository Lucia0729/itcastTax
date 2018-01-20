package cn.itcast.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestLog {
	Log log = LogFactory.getLog(TestLog.class);
	@Test
	public void test(){
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		log.fatal("fatal");
	}
}
