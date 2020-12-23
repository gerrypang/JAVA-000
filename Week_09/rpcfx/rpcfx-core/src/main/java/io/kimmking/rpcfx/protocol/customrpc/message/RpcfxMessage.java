package io.kimmking.rpcfx.protocol.customrpc.message;

public final class RpcfxMessage<T> {

	/** 消息头 */
	private RpcfxHeader header;

	/** 消息体 */
	private T body;

	public RpcfxHeader getHeader() {
		return header;
	}

	public void setHeader(RpcfxHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	
}
