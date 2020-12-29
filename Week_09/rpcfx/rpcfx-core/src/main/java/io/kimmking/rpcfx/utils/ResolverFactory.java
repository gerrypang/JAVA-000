package io.kimmking.rpcfx.utils;

import java.util.Iterator;
import java.util.ServiceLoader;

import io.kimmking.rpcfx.api.RpcfxResolver;

public class ResolverFactory {
	private ResolverFactory() {  
    } 
	
	public static RpcfxResolver getRpcfxResolver() {
		RpcfxResolver resolver = null;
		ServiceLoader<RpcfxResolver> serviceLoader = ServiceLoader.load(RpcfxResolver.class);  
        Iterator<RpcfxResolver> resolverCols = serviceLoader.iterator();  
        if (resolverCols.hasNext()) {  
        	resolver = resolverCols.next();  
        }
		return resolver; 
	}
}
