package com.gerry.pang.common.demo.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class EchoClient {
	// 保存在回复这个客户端所有信息
	private LinkedList<ByteBuffer> outq;

	public EchoClient() {
		outq = new LinkedList<>();
	}

	public LinkedList<ByteBuffer> getOutputQueue() {
		return outq;
	}

	public void enqueue(ByteBuffer buffer) {
		outq.add(buffer);
	}
}
