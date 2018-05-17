package com.movie.daoimpl;


import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.movie.dao.BaseDao;

@Repository
@Lazy(true)
public class BaseDaoImpl<T> implements BaseDao<T>{

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	} 	

	@Override
	public void save(T t) {
		getSession().save(t);
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public void delete(int id) {
		String hql = "delete"+" "+clazz.getSimpleName()+" "+"as c where c.id = :id ";
		getSession().createQuery(hql).setInteger("id", id).executeUpdate();
	}

}
