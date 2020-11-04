package io.github.kimmking.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kimmking.gateway.client.enums.ClientTypeEnum;
import io.github.kimmking.gateway.inbound.HttpInboundServer;

public class NettyServerApplication {
    
	public static Logger logger = LoggerFactory.getLogger(NettyServerApplication.class);
	
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    public final static String GATEWAY_SERVER = "http://localhost:8890";
    public final static String GATEWAY_PORT = "8890";
    
    public static void main(String[] args) {
		String proxyServer = System.getProperty("proxyServer", GATEWAY_SERVER);
		String proxyPort = System.getProperty("proxyPort", GATEWAY_PORT);
        
		// http://localhost:8890/xxx ==> gateway API
		// http://localhost:8087/api/hello ==> backend service
		// http://localhost:8088/api/hello ==> backend service
		// http://localhost:8089/api/hello ==> backend service

        int port = Integer.parseInt(proxyPort);
        logger.info("{} {} starting...", GATEWAY_NAME, GATEWAY_VERSION);
        // 可以根据 ClientTypeEnum 枚举类型调整 client的类型，暂不支持netty
        HttpInboundServer server = new HttpInboundServer(port, proxyServer, ClientTypeEnum.HTTP_ASYC_CLIENT);
        logger.info("{} {} started at http://localhost:{} for server:{}", GATEWAY_NAME, GATEWAY_VERSION, port, proxyServer);
		try {
			server.run();
		} catch (Exception ex) {
			logger.error("start failure, cause:{}", ex);
		}
    }
}
