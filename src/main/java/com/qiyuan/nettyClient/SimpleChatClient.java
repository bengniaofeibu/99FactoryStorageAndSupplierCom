package com.qiyuan.nettyClient;//package com.qiyuan.nettyClient;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
//public class SimpleChatClient {
//	private final String host;
//    private final int port;
//    //private ChannelFuture f;
//    private  Channel channel;
//
//    public SimpleChatClient(String host, int port){
//        this.host = host;
//        this.port = port;
//    }
//
//    public void run() throws Exception{
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            Bootstrap bootstrap  = new Bootstrap()
//                    .group(group)
//                    .channel(NioSocketChannel.class)
//                    .handler(new SimpleChatClientInitializer());
//            //Channel channel = bootstrap.connect(host, port).sync().channel();
//            // Start the client.
//            //f = bootstrap.connect(host, port).sync();
//
//            channel = bootstrap.connect(host, port).sync().channel();
//
//            // Wait until the connection is closed.
//           // f.channel().closeFuture().sync();
//
//            //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
////            while(true){
////                channel.writeAndFlush(in.readLine() + "\r\n");
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public void sendMessage(){
//    	//Channel channel=f.channel();
//    	  System.out.println("HelloClientIntHandler.channelActive");
//    	  //7E 00 02 00 00 06 30 73 96 78 34 00 04 99 7E
//	        byte[] msg = new byte[15];
//	        msg[0] = 0x7E;
//	        msg[1] = 0x00;
//	        msg[2] = 0x02;
//	        msg[3] = 0x00;
//	        msg[4] = 0x00;
//	        msg[5] = 0x06;
//	        msg[6] = 0x30;
//	        msg[7] = 0x73;
//	        msg[8] = (byte) 0x96;
//	        msg[9] = 0x78;
//	        msg[10] = 0x34;
//	        msg[11] = 0x00;
//	        msg[12] = 0x04;
//	        msg[13] = (byte) 0x99;
//	        msg[14] = (byte) 0x7E;
//	        ByteBuf encoded = channel.alloc().buffer(4 * msg.length);
//	        encoded.writeBytes(msg);
//	        channel.writeAndFlush(encoded);
//    }
//}
