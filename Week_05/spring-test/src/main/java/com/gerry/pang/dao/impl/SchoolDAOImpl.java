package com.gerry.pang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gerry.pang.dao.SchoolDAO;
import com.gerry.pang.domain.School;

@Repository
public class SchoolDAOImpl implements SchoolDAO {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public School saveOne(School domain) {
		String sql = "insert into tb_school(name, code) values(?, ?)";
		int rs = template.update(sql, domain.getName(), domain.getCode());
		if (rs != 0) {
			domain.setId(rs);
		}
		return domain;
	}

	@Override
	public boolean updateOne(School domain) {
		String sql = "update tb_school set name=?, code=? where 1=1 and id=?";
		int rs = template.update(sql, domain.getName(), domain.getCode(), domain.getId());
		if (rs != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteOne(School domain) {
		String sql = "delete from tb_school where 1=1 and id=?";
		int rs = template.update(sql, domain.getId());
		if (rs != 0) {
			return true;
		}
		return false;
	}

	@Override
	public School selectOne(School domain) {
		String sql = "select id, name, code from tb_school where 1=1 and id=?";
		RowMapper<School> rowMapper = new BeanPropertyRowMapper<>(School.class);
		School school = template.queryForObject(sql, rowMapper, domain.getId());
		return school;
	}

	@Override
	public Boolean updateBatch(List<School> updateList) {
		String sql = "update tb_school set name=?, code=? where 1=1 and id = ? ;";
		List<Object[]> batchArgs = new ArrayList<>(10);
		if (CollectionUtils.isNotEmpty(updateList)) {
			updateList.forEach(n -> {
				batchArgs.add(new Object[] { n.getName(), n.getCode(), n.getId() }); 	 
			});
		}
		int[] rs = template.batchUpdate(sql, batchArgs);
		if (rs != null) {
			return true;
		}
		return false;
	}


}
