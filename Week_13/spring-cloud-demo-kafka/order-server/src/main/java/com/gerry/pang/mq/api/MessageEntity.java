package com.gerry.pang.mq.api;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageEntity<H, B> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String messageId;
	
	private LocalDateTime sendTime;

	public H messageHeader;
	
	public B messageBody;
}
