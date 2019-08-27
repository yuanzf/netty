package BIOandNIO.AIO;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: yzf
 * @Date: 2019-08-25 23:22
 * @Desoription:
 */
public class AIOAsyncTimerServerHandler implements Runnable {

    private int port;

    private CountDownLatch latch;

    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AIOAsyncTimerServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The Time Server is start in port :" + port);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept(){
        asynchronousServerSocketChannel.accept(this, new AIOAcceptCompletionHandler());
    }

    public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel(){
        return this.asynchronousServerSocketChannel;
    }

    public CountDownLatch getLatch(){
        return latch;
    }
}
