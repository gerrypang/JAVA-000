package com.gerry.pang.order.listenter;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gerry.pang.order.service.impl.OrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息监听处理
 * 
 * @author pangguowei
 */
@Slf4j
@Component
public class PayOrderRedisListener extends MessageListenerAdapter {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.info(">>>>> PayOrderRedisListener onMessage start, message:{}", JSON.toJSONString(message));
		// 获得当前消息的主题名称
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        // 反序列化回消息（如果确认发送的消息类型的话，可以强转）
        Object messageValue = redisTemplate.getValueSerializer().deserialize(message.getBody());
        // 区分主题，然后做不同的处理
        if (OrderServiceImpl.CHANNEL_NAME.equals(channel)) {
             log.info("===> handle pay order message :{}", messageValue);
        } 
	}

	@Override
	protected void handleListenerException(Throwable ex) {
		super.handleListenerException(ex);
		log.error("PayOrderRedisListener 监听消息处理异常：{}", ex);
	}
	
	 
}
