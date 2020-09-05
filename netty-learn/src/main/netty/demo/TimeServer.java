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
        //bossGroup负责接收客户端连接并将SocketChannel交给WorkerEventLoopGroup来进行IO处理
        //EventLoop维护这一个注册了ServerSocketChannel的Selector实例，BoosEventGroup不断轮询selector将连接事件分离出来
        //通常是OP_ACCETP事件然后将接收到的SocketChannel交给WorkerEventLoopGroup。
        //WorkerEventLoopGroup会由next选择其中一个EventLoopGroup来将这个SocketChannel注册到其卫华的Selector并
        // 对后续的IO事件进行处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            //设置工作组合接收组
            b.group(bossGroup,workerGroup)
                    //创建NioServerSocketChannel工厂类，后期需要新建ServerSocket只需调用newInstance方法即可
                    .channel(NioServerSocketChannel.class)
                    /**设定serverChannel参数,用来初始化服务器可连接队列大小，服务端处理客户端连接请求是按照顺序处理
                     * 的，所以同一时间只能处理一个客户端的连接，多个客户端来的时候，服务器端将不能处理的客户端连接请
                     * 求放在队中等待处理，backlog参数指定的就是队列的大小
                     */
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler());
            //绑定端口同步等待成功()服务端创建的入口
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
            //ChannelPipeline在链路创建的时候初始化。
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        new TimeServer().bind(port);
    }
}
