package com.gerry.pang.common.demo.disruptor;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String value;

}
