package com.qiyuan.common;//package com.qiyuan.common;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//
//import java.util.List;
//
//import com.qiyuan.protocol.T808Message;
//
///**
// * 808鍗忚瑙ｆ瀽鍣紝涓撶敤浜巒ettry server
// *
// */
//public class ProtocolDecoder extends ByteToMessageDecoder {
//	private static final int MIN_HEADER_SIZE = 10;
//
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
//			List<Object> out) throws Exception {
//		if (in == null) {
//			return;
//		}
//
//		if (in.readableBytes() < MIN_HEADER_SIZE) {
//			return;// response header is 10 bytes
//		}
//		in.markReaderIndex();
//		// this.logger.warn(Tools.ToHexString(data));
//		// int pos = 0;
//		while (in.isReadable()) {
//			in.markReaderIndex();
//			int packetBeginIndex = in.readerIndex();
//			byte tag = in.readByte();
//			// 鎼滅储鍖呯殑寮�浣嶇疆
//			if (tag == 0x7E && in.isReadable()) {
//				tag = in.readByte();
//				// 闃叉鏄袱涓�x7E,鍙栧悗闈㈢殑涓哄寘鐨勫紑濮嬩綅缃�
//				// 瀵绘壘鍖呯殑缁撴潫
//				while (tag != 0x7E) {
//					if (in.isReadable() == false) {
//						in.resetReaderIndex(); // 娌℃湁鎵惧埌缁撴潫鍖咃紝绛夊緟涓嬩竴娆″寘
//						// logger.error("鍗婂寘:"+Tools.ToHexString(data));
//						return;
//					}
//					tag = in.readByte();
//				}
//				int pos = in.readerIndex();
//				int packetLength = pos - packetBeginIndex;
//				if (packetLength > 1) {
//					byte[] tmp = new byte[packetLength];
//					in.resetReaderIndex();
//					in.readBytes(tmp);
//					T808Message message = new T808Message();
//					message.ReadFromBytes(tmp);
//					out.add(message); // 瑙﹀彂鎺ユ敹Message鐨勪簨浠�
//					// return message;
//				} else {
//					// 璇存槑鏄袱涓�x7E
//					in.resetReaderIndex();
//					in.readByte(); // 涓や釜7E璇存槑鍓嶉潰鏄寘灏撅紝鍚庨潰鏄寘澶�
//				}
//			}
//		}
//
//		return;
//	}
//
//
//}
