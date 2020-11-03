package io.github.kimmking.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kimmking.gateway.inbound.HttpInboundServer;

public class NettyServerApplication {
    
	public static Logger log = LoggerFactory.getLogger(NettyServerApplication.class);
	
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    
    public static void main(String[] args) {
		String proxyServer = System.getProperty("proxyServer", "http://localhost:8088");
		String proxyPort = System.getProperty("proxyPort", "8888");
        
		// http://localhost:8888/api/hello ==> gateway API
		// http://localhost:8088/api/hello ==> backend service

        int port = Integer.parseInt(proxyPort);
        log.info("{} {} starting...", GATEWAY_NAME, GATEWAY_VERSION);
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        log.info("{} {} started at http://localhost:{} for server:{}", GATEWAY_NAME, GATEWAY_VERSION, port, proxyServer);
		try {
			server.run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}
