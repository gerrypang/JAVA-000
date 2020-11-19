package com.gerry.pang.dao.base;

public interface BaseDAO<T> {
	
	T saveOne(T domain);

	boolean updateOne(T domain);

	boolean deleteOne(T domain);

	T selectOne(T domain);
}
