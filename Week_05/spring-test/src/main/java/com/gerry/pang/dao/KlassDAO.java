package com.gerry.pang.dao;

import java.util.List;

import com.gerry.pang.dao.base.BaseDAO;
import com.gerry.pang.domain.Klass;


public interface KlassDAO extends BaseDAO<Klass> {
	
	boolean updateBatch(List<Klass> domains);
	
}
