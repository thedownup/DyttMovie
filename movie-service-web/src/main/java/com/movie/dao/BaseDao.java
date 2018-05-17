package com.movie.dao;

public interface BaseDao<T> {

	public void save(T t);

	public void update(T t);

	public void delete(int id);

}
