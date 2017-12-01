package com.qiyuan.nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleServer {


         public  void  bindServer() {

             EventLoopGroup group=new NioEventLoopGroup();
             EventLoopGroup work =new NioEventLoopGroup();

             try {

             ServerBootstrap bootstrap=new ServerBootstrap();
             bootstrap.group(group,work)
                     .channel(NioServerSocketChannel.class)
                     .option(ChannelOption.SO_BACKLOG,1024)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) throws Exception {
                             ch.pipeline().addLast(new SimPleServerHandle());
                         }
                     });

             ChannelFuture sync = bootstrap.bind(8080).sync();
             sync.channel().closeFuture().sync();
             }catch (Exception e){
                 group.shutdownGracefully();
                 work.shutdownGracefully();
             }
         }

    public static void main(String[] args) {
        SimpleServer server=new SimpleServer();
        server.bindServer();
    }
}
