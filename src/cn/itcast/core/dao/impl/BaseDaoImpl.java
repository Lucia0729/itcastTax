package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;

@Repository("baseDao")
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{
	private Class<T> clazz;
	public BaseDaoImpl(){
		ParameterizedType pt =  (ParameterizedType)this.getClass().getGenericSuperclass();//BaseDaoImpl<User>
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	} 
	
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
		getHibernateTemplate().delete(findObjectById(id));
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}

	@Override
	public T findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects() {
		// TODO Auto-generated method stub
		Query query = currentSession().createQuery("FROM "+ clazz.getSimpleName());
		return query.list();
	}
	
	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		// TODO Auto-generated method stub
		 Query query = currentSession().createQuery(hql); 
		if(parameters != null){
	            for(int i = 0; i < parameters.size(); i++){
	                query.setParameter(i, parameters.get(i));
	            }
	        }
	        return query.list();
	}
	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		// TODO Auto-generated method stub
		 Query query = currentSession().createQuery(queryHelper.getQueryListHql()); 
		 List<Object> parameters = queryHelper.getParameters() ;
		 if(parameters != null){
	            for(int i = 0; i < parameters.size(); i++){
	                query.setParameter(i, parameters.get(i));
	            }
	        }
	      return query.list();
	}
	
	public PageResult findObjects(QueryHelper queryHelper, int pageNo, int pageSize){
		 Query query = currentSession().createQuery(queryHelper.getQueryListHql()); 
		 List<Object> parameters = queryHelper.getParameters() ;
		 if(parameters != null){
	            for(int i = 0; i < parameters.size(); i++){
	                query.setParameter(i, parameters.get(i));
	            }
	        }
		 if(pageNo<1) pageNo=0;
		 query.setFirstResult((pageNo-1)*pageSize);
		 query.setMaxResults(pageSize);
	     List list = query.list();
		 Query queryCount = currentSession().createQuery(queryHelper.getQueryCountHql()); 
		 if(parameters != null){
	            for(int i = 0; i < parameters.size(); i++){
	            	queryCount.setParameter(i, parameters.get(i));
	            }
	        }
		 long totalCount = (long) queryCount.uniqueResult();
		
		 return new PageResult(totalCount, pageNo, pageSize, list);
	     
	}
	

}
