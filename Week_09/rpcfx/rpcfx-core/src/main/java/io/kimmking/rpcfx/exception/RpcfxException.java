package io.kimmking.rpcfx.exception;

/**
 * RpcfxException
 * 
 * @author pangguowei
 */
public class RpcfxException extends Exception {
 
	private static final long serialVersionUID = 1L;

	public RpcfxException() {
		super();
	}

	public RpcfxException(String message) {
		super(message);
	}

	public RpcfxException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
