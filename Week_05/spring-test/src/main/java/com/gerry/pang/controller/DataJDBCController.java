package com.gerry.pang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.dao.KlassDAO;
import com.gerry.pang.domain.Klass;

@RestController
@RequestMapping("/data/jdbc")
public class DataJDBCController {
	
	@Autowired
	private KlassDAO klassDAO;
	
	@GetMapping("/save")
	public Klass jdbcSaveDemo() {
		Klass klass = klassDAO.saveOne(Klass.builder().code("K999").name("K-name").build());
		return klass;
	}

	@GetMapping("/select")
	public Klass jdbcSelectDemo() {
		Klass klass = klassDAO.selectOne(Klass.builder().id(1).build());
		return klass;
	}

	@GetMapping("/update")
	public Boolean jdbcUpdateDemo() {
		Boolean result = klassDAO.updateOne(Klass.builder().id(1).code("K888").name("K-name-1").build());
		return result;
	}
	
	@GetMapping("/update/batch")
	public Boolean jdbcUpdateBatchDemo() {
		List<Klass> updateList = new ArrayList<>(10);
		updateList.add(Klass.builder().id(1).code("K1").name("K-name-1").build());
		updateList.add(Klass.builder().id(2).code("K2").name("K-name-2").build());
		updateList.add(Klass.builder().id(3).code("K3").name("K-name-3").build());	
		Boolean result = klassDAO.updateBatch(updateList);
		return result;
	}

	@GetMapping("/delete")
	public Boolean jdbcDeleteDemo() {
		Boolean result = klassDAO.deleteOne(Klass.builder().id(1).build());
		return result;
	}
}
