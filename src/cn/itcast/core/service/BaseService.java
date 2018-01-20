package cn.itcast.core.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public interface BaseService<T> {
	public void save(T entity);
	public void delete(Serializable id);
	public void update(T entity);
	public T findObjectById(Serializable id);
	public List<T> findObjects();
	@Deprecated
	public List<T> findObjects(String hql,List<Object> parameters);
	public List<T> findObjects(QueryHelper queryHelper);
}
