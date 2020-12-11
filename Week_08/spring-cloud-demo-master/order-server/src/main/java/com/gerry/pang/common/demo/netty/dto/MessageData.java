package com.gerry.pang.common.demo.netty.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String type;
	
	private LocalDateTime sendTime;
	
	private String message;

}
