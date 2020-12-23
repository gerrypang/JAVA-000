package io.kimmking.rpcfx.utils;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public final class MarshallingCodeCFactory {

	private MarshallingCodeCFactory() {
		super();
	}

	/**
	 * 创建JBoss Marshalling解码器
	 *
	 * @return
	 * @author pangguowei
	 */
	public static MarshallingDecoder buildMarshallingDecoder() {
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		MarshallingDecoder decoder = new MarshallingDecoder(provider);
		return decoder;
	}

	/**
	 * 创建JBoss Marshalling编码器
	 *
	 * @return
	 * @author pangguowei
	 */
	public static MarshallingEncoder buildMarshallingeEncoder() {
		// "serial"表示创建的是Java序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		// MarshallingEncoder用于将实现序列化接口的POJO对象序列化为二进制字节数组
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}

}
