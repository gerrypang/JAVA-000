package io.kimmking.rpcfx.demo.provider.resolver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.kimmking.rpcfx.api.RpcfxResolver;

public class SimpleResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolve(String serviceClass) throws Exception {
    	Object result = null;
    	
    	// by name
    	if (applicationContext.containsBean(serviceClass)) {
    		result = this.applicationContext.getBean(serviceClass);
    		return result;
    	}
    	
        // by type
        if (result == null) {
        	Class<?> clazzType = null;
			clazzType = Class.forName(serviceClass);
			result = this.applicationContext.getBean(clazzType);
        }
        
        return result;
    }
}
