package io.kimmking.rpcfx.client.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.connector.Connector;

public  class RpcfxInvocationHandler implements InvocationHandler {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }
    
    private final Class<?> serviceClass;
    
    private final String url;
    
    private final Connector connector;
   
    public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Connector connector) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.connector = connector;
    }

    // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
    // int byte char float double long bool
    // [], data class

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethod(method.getName());
        request.setParams(params);

        RpcfxResponse response = connector.sendRequest(request, url);

        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException

        return JSON.parse(response.getResult().toString());
    }

}
