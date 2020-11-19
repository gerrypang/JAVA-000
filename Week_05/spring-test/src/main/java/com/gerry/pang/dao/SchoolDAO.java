package com.gerry.pang.dao;

import java.util.List;

import com.gerry.pang.dao.base.BaseDAO;
import com.gerry.pang.domain.School;

public interface SchoolDAO extends BaseDAO<School> {

	Boolean updateBatch(List<School> updateList);
	
}
