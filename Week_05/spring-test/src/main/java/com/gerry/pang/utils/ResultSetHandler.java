package com.gerry.pang.utils;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler<T> {
	
	T handle(ResultSet resultSet);

	List<T> listHandle(ResultSet resultSet);
	
}
