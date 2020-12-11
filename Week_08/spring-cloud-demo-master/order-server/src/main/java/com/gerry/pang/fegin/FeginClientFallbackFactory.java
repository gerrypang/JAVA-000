package com.gerry.pang.fegin;

import org.springframework.stereotype.Component;

import com.gerry.pang.fegin.dto.User;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeginClientFallbackFactory implements FallbackFactory<UserFeign> {

	@Override
	public UserFeign create(Throwable cause) {
		
		return new UserFeign() {
			@Override
			public User getUser(String id) {
				log.info("===> fallback reason was : {}", cause);
				User user = new User();
				user.setId("-1");
				user.setUsername("默认用户");
				return user;
			}
			
		};
	}

}
