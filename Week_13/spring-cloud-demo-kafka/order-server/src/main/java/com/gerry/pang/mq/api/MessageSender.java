package com.gerry.pang.mq.api;

public interface MessageSender {
	
	public void configSender();
	
	public void sendMessage(MessageEntity<?, ?> msgEntity);
	
	public void asyncSendMessage(MessageEntity<?, ?> msgEntity);
}
