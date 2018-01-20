package cn.itcast.nsfw.info.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.info.dao.InfoDao;
import cn.itcast.nsfw.info.entity.Info;

@Repository("infoDao")
public class InfoDaoImpl extends BaseDaoImpl<Info> implements InfoDao {

}
