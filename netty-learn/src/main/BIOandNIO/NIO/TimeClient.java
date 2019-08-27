package BIOandNIO.NIO;

/**
 * @Author: yzf
 * @Date: 2019-08-19 23:59
 * @Desoription:
 */
public class TimeClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandle(8080) ,"TimeClient-001").start();
    }
}
