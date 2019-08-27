package BIOandNIO.NIO;

/**
 * @Author: yzf
 * @Date: 2019-08-19 23:02
 * @Desoription:
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
        new Thread(multiplexerTimeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
