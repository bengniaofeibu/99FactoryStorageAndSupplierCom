package com.qiyuan.nettyClient;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleChatClientHandler extends ChannelHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf=(ByteBuf)msg;
        byte[] byt=new byte[buf.readableBytes()];
        buf.readBytes(byt);
        System.out.println(new String(byt));
    }

    // 连接成功后，向server发送消息
	    @Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("连接成功");

            String message="你好吗";
            byte[] bytes = message.getBytes();
            ByteBuf buf= Unpooled.buffer(bytes.length);
            buf.writeBytes(bytes);
            ctx.writeAndFlush(buf);
        }
}
