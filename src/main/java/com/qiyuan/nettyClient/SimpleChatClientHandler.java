package com.qiyuan.nettyClient;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
	 @Override
	 protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
		 System.out.println(s);
	 }
	 
	 // 连接成功后，向server发送消息  
	    @Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
//	       System.out.println("HelloClientIntHandler.channelActive");  
//	        String msg = "Are you ok?";  
//	        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());  
//	        encoded.writeBytes(msg.getBytes());  
//	        ctx.write(encoded);  
//	        ctx.flush();  
	    }  
}
