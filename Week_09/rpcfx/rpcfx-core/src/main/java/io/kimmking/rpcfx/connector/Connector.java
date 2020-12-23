package io.kimmking.rpcfx.connector;

import java.io.IOException;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;

public interface Connector {
	
	RpcfxResponse sendRequest(RpcfxRequest req, String url) throws IOException ;
	
	
}
