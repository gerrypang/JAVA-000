package com.gerry.pang.common.demo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {

	private Selector selector;
	private ExecutorService threadPool = Executors.newFixedThreadPool(5);

	public void startServer() {
		try {
			// 通过工厂方法获取Selector对象
			selector = SelectorProvider.provider().openSelector();
			// 获取服务端SocketChannel
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			// SocketChannel设置为非阻塞
			serverSocketChannel.configureBlocking(false);
			// 进行端口绑定
			InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 9990);
			serverSocketChannel.bind(inetSocketAddress);

			// 将serverSocketChannel绑定到selector，并注册事件为accept，SelectionKey表示selector和channel的关系
			SelectionKey acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			// 等待-分发网络消息
			for (;;) {
				// select阻塞直到有数据可读
				selector.select();
				// 返回已经准备好SelectionKey
				Set<SelectionKey> readKeys = selector.selectedKeys();
				// 遍历
				Iterator<SelectionKey> it = readKeys.iterator();
				while (it.hasNext()) {
					SelectionKey skey = it.next();
					// 注意：将key移除，否则会重复处理相同的key
					it.remove();

					// 判断是否为Acceptable状态
					if (skey.isAcceptable()) {
						this.doAccept(skey);
					}

					// 判断是否可读状态
					else if (skey.isValid() && skey.isReadable()) {
						this.doRead(skey);
					}

					// 判断是否可写状态
					else if (skey.isValid() && skey.isWritable()) {
						this.write(skey);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doAccept(SelectionKey skey) {
		ServerSocketChannel server = (ServerSocketChannel) skey.channel();
		SocketChannel clientChannel;
		try {
			// 生成clientChannel 和客户端的通道
			clientChannel = server.accept();
			clientChannel.configureBlocking(false);
			// SelectionKey表示selector和channel的关系
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			EchoClient echoClient = new EchoClient();
			// 将客户端作为附件加到SelectionKey，链接过程共享echoClient
			clientKey.attach(echoClient);
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			log.info("Accepted connection from {}", clientAddress.getHostAddress());
		} catch (Exception e) {
			log.error("Failed to accept new client, {}", e);
		}
	}

	private void doRead(SelectionKey skey) {
		SocketChannel channel = (SocketChannel) skey.channel();
		// 准备8k缓存区读取数据
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		int len;
		try {
			len = channel.read(buffer);
			if (len < 0) {
				this.disconnect(skey);
				return;
			}
		} catch (Exception e) {
			log.error("Failed to read from client, {}", e);
			disconnect(skey);
			return;
		}

		buffer.flip();
		threadPool.execute(new HandlMsg(skey, buffer));
	}

	private void write(SelectionKey skey) {
		SocketChannel channel = (SocketChannel) skey.channel();
		EchoClient client = (EchoClient) skey.attachment();
		LinkedList<ByteBuffer> outq = client.getOutputQueue();
		ByteBuffer buffer = outq.getLast();

		try {
			// 进行数据回写
			int len = channel.write(buffer);
			if (len == -1) {
				this.disconnect(skey);
				return;
			}
			// 如果全部发送完成，则移除这个缓存对象
			if (buffer.remaining() == 0) {
				outq.removeLast();
			}
		} catch (Exception e) {
			log.error("Failed to write to client, {}", e);
			disconnect(skey);
		}

		// 注意：在数据全部发送完成后，将写事件从感兴趣操作中移除
		if (outq.size() == 0) {
			skey.interestOps(SelectionKey.OP_READ);
		}
	}

	private void disconnect(SelectionKey skey) {
		SocketChannel channel = (SocketChannel) skey.channel();
		try {
			channel.close();
		} catch (IOException e) {
			log.error("Failed to closed channel, {}", e);
		}
	}

}
