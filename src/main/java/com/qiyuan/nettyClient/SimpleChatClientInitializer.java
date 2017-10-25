package com.qiyuan.nettyClient;//package com.qiyuan.nettyClient;
//
//import com.qiyuan.common.ProtocolDecoder;
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.bytes.ByteArrayDecoder;
//import io.netty.handler.codec.bytes.ByteArrayEncoder;
//import io.netty.handler.codec.serialization.ObjectEncoder;
//import io.netty.handler.codec.string.StringDecoder;
//import io.netty.handler.codec.string.StringEncoder;
//import io.netty.util.CharsetUtil;
//
//public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel>{
//	@Override
//    public void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline pipeline = ch.pipeline();
//
//        pipeline.addLast("decoder", new ByteArrayDecoder());
//		pipeline.addLast("encoder", new ByteArrayEncoder());
//        pipeline.addLast("handler", new SimpleChatClientHandler());
//    }
//}
