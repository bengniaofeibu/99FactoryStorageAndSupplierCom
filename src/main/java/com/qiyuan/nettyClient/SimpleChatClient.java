package com.qiyuan.nettyClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleChatClient {
	private final String host;
    private final int port;
    //private ChannelFuture f;
    private  Channel channel;

    public SimpleChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new SimpleChatClientInitializer());

            channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().sync();

        } catch (Exception e) {
          group.shutdownGracefully();
        }
    }

	public static void main(String[] args) throws Exception {
		SimpleChatClient simpleChatClient=new SimpleChatClient("127.0.0.1",8080);
         simpleChatClient.run();
	}
}
