package com.qiyuan.nettyClient;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketClient {
	
	public static byte[] creatSocket(byte[] bytes,  String ip, int port) {
		// 创建socket通信			
		SocketAddress remoteAddress = new InetSocketAddress(ip, port);// IP地址和端口号
		Socket socket = null;
		byte[] b = null;
		try {
			socket = new Socket();
			socket.connect(remoteAddress, 10000);
			socket.setSoTimeout(15000);
			// socket.setSoTimeout(1500000);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			out.write(bytes);
			out.flush();
			int readBytes = 0;
			b = new byte[20];
			int len = b.length;
//			while (readBytes < len) {
//				int read = in.read(b, readBytes, len - readBytes);
//				if (read == -1) {
//					break;
//				}
//				readBytes += read;
//			}
			System.out.println("close socket");
			out.close(); // 关闭Socket输出流
			in.close(); // 关闭Socket输入流
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
