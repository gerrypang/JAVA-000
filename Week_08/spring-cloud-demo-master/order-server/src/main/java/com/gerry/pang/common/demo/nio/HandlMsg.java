package com.gerry.pang.common.demo.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class HandlMsg implements Runnable {

	private SelectionKey skey;
	private ByteBuffer buffer;

	public HandlMsg(SelectionKey skey, ByteBuffer buffer) {
		super();
		this.skey = skey;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		// 将接收到数据压入队列
		EchoClient client = (EchoClient) skey.attachment();
		client.enqueue(buffer);
		skey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

}
