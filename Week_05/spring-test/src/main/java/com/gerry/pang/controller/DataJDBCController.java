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
@RequestMapping("/data")
public class DataJDBCController {
	
	@Autowired
	private KlassDAO klassDAO;
	
	@GetMapping("/jdbc/save")
	public Klass jdbcSaveDemo() {
		Klass klass = klassDAO.saveOne(Klass.builder().code("K999").name("K-name").build());
		return klass;
	}

	@GetMapping("/jdbc/select")
	public Klass jdbcSelectDemo() {
		Klass klass = klassDAO.selectOne(Klass.builder().id(1).build());
		return klass;
	}

	@GetMapping("/jdbc/update")
	public Boolean jdbcUpdateDemo() {
		Boolean result = klassDAO.updateOne(Klass.builder().id(1).code("K888").name("K-name-1").build());
		return result;
	}
	
	@GetMapping("/jdbc/update/batch")
	public Boolean jdbcUpdateBatchDemo() {
		List<Klass> updateList = new ArrayList<>(10);
		updateList.add(Klass.builder().id(1).code("K1").name("K-name-1").build());
		updateList.add(Klass.builder().id(2).code("K2").name("K-name-2").build());
		updateList.add(Klass.builder().id(3).code("K3").name("K-name-3").build());	
		Boolean result = klassDAO.updateBatch(updateList);
		return result;
	}

	@GetMapping("/jdbc/delete")
	public Boolean jdbcDeleteDemo() {
		Boolean result = klassDAO.deleteOne(Klass.builder().id(1).build());
		return result;
	}
}
