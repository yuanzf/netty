package netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: yzf
 * @Date: 2019-08-27 22:37
 * @Desoription:
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctc,ByteBuf buf) throws Exception{
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        String body = new String(req,"UTF-8");
    }
}
