package com.gerry.pang.dao.base;

public interface BaseDAO<T> {
	
	T saveOne(T domain);

	T updateOne(T domain);

	boolean deleteOne(T domain);

	T selectOne(T domain);
}
