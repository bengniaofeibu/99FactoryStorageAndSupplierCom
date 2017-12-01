package com.qiyuan.nettyClient;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketClient {
	
	public static byte[] creatSocket(byte[] bytes,  String ip, int port) {
		// 创建socket通信			
		SocketAddress remoteAddress = new InetSocketAddress(ip, port);// IP地址和端口号
		Socket socket = null;
		ServerSocket serverSocket=null;
		byte[] b = null;
		try {
			serverSocket=new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("客户端异常:" + e.getMessage());
		} finally { // 执行关闭socket连接
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					System.out.println("客户端 finally 异常:" + e.getMessage());
					return null;
				}
			}
		}
		return b;
	}
}
