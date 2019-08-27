package BIOandNIO.AIO;

/**
 * @Author: yzf
 * @Date: 2019-08-25 23:21
 * @Desoription:
 */
public class AIOTimeServer {
    public static void main(String[] args) {
        int port = 8080;
        AIOAsyncTimerServerHandler aioAsyncTimerServerHandler = new AIOAsyncTimerServerHandler(port);
        new Thread(aioAsyncTimerServerHandler,"AIO-AsyncTimeServerHandler-001").start();
    }
}
