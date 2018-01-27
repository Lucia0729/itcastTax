package cn.itcast.nsfw.complain.dao.impl;


import org.springframework.stereotype.Repository;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;

@Repository("complainDao")
public class ComplainDaomImpl extends BaseDaoImpl<Complain> implements ComplainDao {

	

}
