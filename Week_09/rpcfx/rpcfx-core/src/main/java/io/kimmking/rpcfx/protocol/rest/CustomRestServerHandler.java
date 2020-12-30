package io.kimmking.rpcfx.protocol.rest;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

public class CustomRestServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	
	private static final Log log = LogFactory.getLog(CustomRestServerHandler.class);
	
	private HttpRequest request;
	
	private RpcfxResolver resolver;
	
	public void setResolver(RpcfxResolver resolver) {
		this.resolver = resolver;
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("===== 请求连接成功 ===== ");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("===== 请求连接被断开 ===== ");
//		final EventLoop eventLoop = ctx.channel().eventLoop();
//		eventLoop.schedule(() -> {
//			
//		}, 20, TimeUnit.SECONDS);
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("server exception: " + cause.getMessage());
		Channel channel = ctx.channel();
		if (channel.isActive()) {
			ctx.close();
		}
	}
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		log.info("===== CustomRestServerHandler read0 start ===== ");
		
		if (msg instanceof HttpRequest) {
			request = (HttpRequest) msg;
            log.info("VERSION: " + request.protocolVersion());
            log.info("URI: " + request.uri());

            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            
            if (!request.headers().isEmpty()) {
                for (CharSequence name: request.headers().names()) {
                    for (CharSequence value: request.headers().getAll(name)) {
                    	log.info("HEADER: " + name + " = " + value);
                    }
                }
            }
            
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Entry<String, List<String>> p: params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
						log.info("PARAM: " + key + " = " + val);
                    }
                }
            }
        }
		
        if (msg instanceof HttpContent) {
//        	resolver = ResolverFactory.getRpcfxResolver();
//        	log.info("==> resolver:" + JSON.toJSONString(resolver));
        	
            HttpContent content = (HttpContent) msg;
            String json = content.content().toString(CharsetUtil.UTF_8);
            RpcfxRequest rpcRequest = JSON.parseObject(json, RpcfxRequest.class);
            RpcfxResponse rpcResponse = new RpcfxInvoker(resolver).invoke(rpcRequest);
    		log.info("=== rpcRequest：" + rpcRequest);
    		
            if (content instanceof LastHttpContent) {
            	LastHttpContent trailer = (LastHttpContent) msg;
            	if (!trailer.trailingHeaders().isEmpty()) {
                    for (CharSequence name: trailer.trailingHeaders().names()) {
                        for (CharSequence value: trailer.trailingHeaders().getAll(name)) {
							log.info("TRAILING HEADER: " + name + " = " + value);
						}
                    }
                }
            	
                if (!writeResponse(trailer, ctx, rpcResponse)) {
                	// If keep-alive is off, close the connection once the content is fully written.
                	ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
            
        }
		log.info("===== CustomRestServerHandler read0 end ===== ");
	}
	

    private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx, RpcfxResponse rpcResponse) {
        // Decide whether to close the connection or not.
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, 
                currentObj.decoderResult().isSuccess() ? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(JSON.toJSONString(rpcResponse), CharsetUtil.UTF_8)
        		);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - https://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        // Encode the cookie.
        String cookieString = request.headers().get(HttpHeaderNames.COOKIE);
        if (cookieString != null) {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                for (Cookie cookie: cookies) {
                    response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode("key1", "value1"));
            response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode("key2", "value2"));
        }

        // Write the response.
        ctx.writeAndFlush(response);
        return keepAlive;
    }
	
	private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE, Unpooled.EMPTY_BUFFER);
        ctx.write(response);
    }

}
