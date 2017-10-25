package com.qiyuan.common;//package com.qiyuan.common;
//
//import java.nio.charset.Charset;
//
//import com.qiyuan.protocol.T808Message;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//
///**
// *
// */
//public class ProtocolEncoder extends MessageToByteEncoder<T808Message> {
//
//	/**
//	 *
//	 */
//	public ProtocolEncoder() {
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @param outboundMessageType
//	 */
//	public ProtocolEncoder(Class<? extends T808Message> outboundMessageType) {
//		super(outboundMessageType);
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @param preferDirect
//	 */
//	public ProtocolEncoder(boolean preferDirect) {
//		super(preferDirect);
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @param outboundMessageType
//	 * @param preferDirect
//	 */
//	public ProtocolEncoder(Class<? extends T808Message> outboundMessageType,
//			boolean preferDirect) {
//		super(outboundMessageType, preferDirect);
//		// TODO Auto-generated constructor stub
//	}
//
//	/* (non-Javadoc)
//	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
//	 */
//	@Override
//	protected void encode(ChannelHandlerContext ctx, T808Message msg,
//			ByteBuf out) throws Exception {
//
//		byte[] data = msg.WriteToBytes();
//		out.writeBytes(data);
//	}
//
//}
