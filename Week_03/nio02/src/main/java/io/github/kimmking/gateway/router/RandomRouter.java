package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * 随机路由
 * 
 * @author pangguowei
 * @since 2020年11月3日
 */
public class RandomRouter implements HttpEndpointRouter {

	@Override
	public String route(List<String> endpoints) {
		if (endpoints == null || endpoints.size() == 0) {
			throw new IllegalArgumentException("endpoints is empty");
		}
		int size = endpoints.size();
		Random rand = new Random(); 
		return endpoints.get(rand.nextInt(size));
	}

}
