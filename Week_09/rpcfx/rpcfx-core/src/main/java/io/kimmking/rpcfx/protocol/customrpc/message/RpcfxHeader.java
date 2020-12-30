package io.kimmking.rpcfx.protocol.customrpc.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class RpcfxHeader implements Serializable {

	private static final long serialVersionUID = 1L;

	private int crcCode = 0xabef0101;
	
	/** 消息长度 */
	private int length;
	
	/** 消息类型 */
	private byte type;
	
	/** 附件 */
	private Map<String, Object> attachment = new HashMap<>(16);

	public int getCrcCode() {
		return crcCode;
	}

	public void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Map<String, Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
}
