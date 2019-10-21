package netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: yzf
 * @Date: 2019-08-27 23:28
 * @Desoription:
 */
public class TimeServer {
    public void bind(int port) {

        //acceptor线程组
        //EventLoopGroup是EventExecutor的数组，EventExecutor是执行线程用的。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            //设置工作组合接收组
            b.group(bossGroup,workerGroup)
                    //创建NioServerSocketChannel工厂类，后期需要新建ServerSocket只需调用newInstance方法即可
                    .channel(NioServerSocketChannel.class)
                    //设定TCP参数
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler());
            //绑定端口同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        new TimeServer().bind(port);
    }
}
