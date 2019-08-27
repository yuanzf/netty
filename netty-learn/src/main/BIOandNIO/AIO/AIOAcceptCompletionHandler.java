package BIOandNIO.AIO;

import jdk.management.resource.internal.inst.AsynchronousServerSocketChannelImplRMHooks;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author: yzf
 * @Date: 2019-08-25 23:27
 * @Desoription:
 */
public class AIOAcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AIOAsyncTimerServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AIOAsyncTimerServerHandler attachment) {
        attachment.getAsynchronousServerSocketChannel().accept(attachment,this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer,byteBuffer,new AIOReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AIOAsyncTimerServerHandler attachment) {
        attachment.getLatch().countDown();
    }
}
