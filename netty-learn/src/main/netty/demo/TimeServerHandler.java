package netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

/**
 * @Author: yzf
 * @Date: 2019-08-27 22:37
 * @Desoription:
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("the time server receive order :" + body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
        /*
        could not access io.netty.util.referenceCount
          ByteBuff内存泄漏导致的问题，于是从这方面着手调查，发现netty5默认的分配bytebuff的方式是PooledByteBufAllocator,所以要手动回收，要不然会造成内存泄漏。
          于是释放ByteBuff即可,既增加下面一行即可
          参考连接：https://www.jianshu.com/p/b9241e7a9eda
         */
        ReferenceCountUtil.release(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 激活");
    }

    public void channelReadComplate(ChannelHandlerContext ctx) throws  Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx){
        ctx.close();
    }

}
