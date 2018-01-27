package cn.itcast.core.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;


public interface BaseDao<T> {
	public void save(T entity);
	public void delete(Serializable id);
	public void update(T entity);
	public T findObjectById(Serializable id);
	public List<T> findObjects();
	public List<T> findObjects(String hql, List<Object> parameters);
	public List<T> findObjects(QueryHelper queryHelper);
	public PageResult findObjects(QueryHelper queryHelper, int pageNo, int pageSize);
}
