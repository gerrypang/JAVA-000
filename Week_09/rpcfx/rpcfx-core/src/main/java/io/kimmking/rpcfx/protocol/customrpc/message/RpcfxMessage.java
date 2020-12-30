package io.kimmking.rpcfx.protocol.customrpc.message;

import java.io.Serializable;

public final class RpcfxMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 消息头 */
	private RpcfxHeader header;

	/** 消息体 */
	private Object body;

	public RpcfxHeader getHeader() {
		return header;
	}

	public void setHeader(RpcfxHeader header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	
}
