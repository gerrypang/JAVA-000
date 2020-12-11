package com.gerry.pang.common.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
	private Selector selector;

	public void init(String ip, int port) throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		this.selector = SelectorProvider.provider().openSelector();
		channel.connect(new InetSocketAddress(ip, port));
		channel.register(selector, SelectionKey.OP_CONNECT);
	}

	public void working() throws IOException {
		while (true) {
			if (!selector.isOpen()) {
				break;
			}
			selector.select();
			Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey sKey = (SelectionKey) it.next();
				it.remove();
				if (sKey.isConnectable()) {
					this.connect(sKey);
				}
				else if (sKey.isReadable()) {
					this.read(sKey);
				}
			}
		}
	}

	private void read(SelectionKey sKey) throws IOException {
		SocketChannel channel = (SocketChannel) sKey.channel();
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		log.info("Client receive message :{}", msg);
		channel.close();
		sKey.selector().close();
	}

	private void connect(SelectionKey sKey) throws IOException {
		SocketChannel channel = (SocketChannel) sKey.channel();
		if (channel.isConnectionPending()) {
			channel.finishConnect();
		}
		channel.configureBlocking(false);
		channel.write(ByteBuffer.wrap(new String("hello server").getBytes()));
		channel.register(selector, SelectionKey.OP_READ);
	}
}
