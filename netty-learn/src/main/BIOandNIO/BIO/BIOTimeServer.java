package BIOandNIO.BIO;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: yzf
 * @Date: 2019-07-30 22:12
 * @Desoription:
 */
public class BIOTimeServer {
    public static void main(String[] args) throws Exception{

        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.print("The time Server is start in port " + port) ;
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (Exception exception){

        }finally {
            if (serverSocket != null){
                System.out.println("The time Server close");
                serverSocket.close();
                serverSocket=null;
            }
        }
    }
}
