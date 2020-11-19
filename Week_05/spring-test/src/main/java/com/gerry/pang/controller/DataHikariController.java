package com.gerry.pang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.dao.SchoolDAO;
import com.gerry.pang.domain. School;

@RestController
@RequestMapping("/data/hikari")
public class DataHikariController {
	
	@Autowired
	private SchoolDAO schoolDAO;
	
	@GetMapping("/save")
	public  School jdbcSaveDemo() {
		 School klass = schoolDAO.saveOne( School.builder().code("S999").name("S-name").build());
		return klass;
	}

	@GetMapping("/select")
	public  School jdbcSelectDemo() {
		 School klass = schoolDAO.selectOne( School.builder().id(1).build());
		return klass;
	}

	@GetMapping("/update")
	public Boolean jdbcUpdateDemo() {
		Boolean result = schoolDAO.updateOne( School.builder().id(1).code("S888").name("S-name-1").build());
		return result;
	}
	
	@GetMapping("/update/batch")
	public Boolean jdbcUpdateBatchDemo() {
		List<School> updateList = new ArrayList<>(10);
		updateList.add( School.builder().id(1).code("S1").name("S-name-1").build());
		updateList.add( School.builder().id(2).code("S2").name("S-name-2").build());
		updateList.add( School.builder().id(3).code("S3").name("S-name-3").build());	
		Boolean result = schoolDAO.updateBatch(updateList);
		return result;
	}

	@GetMapping("/delete")
	public Boolean jdbcDeleteDemo() {
		Boolean result = schoolDAO.deleteOne(School.builder().id(1).build());
		return result;
	}
}
