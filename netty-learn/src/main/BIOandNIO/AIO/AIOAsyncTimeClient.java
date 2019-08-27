package BIOandNIO.AIO;

/**
 * @Author: yzf
 * @Date: 2019-08-27 00:22
 * @Desoription:
 */
public class AIOAsyncTimeClient {
    public static void main(String[] args) {
        new Thread(new AIOAsyncTimeClientHandler("127.0.0.1",8080),"AIO-asyncTimeClientHandler-001").start();
    }
}
