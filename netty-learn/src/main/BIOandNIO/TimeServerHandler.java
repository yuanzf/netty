package BIOandNIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: yzf
 * @Date: 2019-08-14 23:07
 * @Desoription:
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket){
        System.out.println("The Time server receive order");
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true){
                body =  in.readLine();
                if (Objects.isNull(body)){
                    break;
                }
                System.out.println("The Time server receive order : "+ body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                System.out.println(currentTime);
                out.println(currentTime);
            }
        }catch (Exception exception){
            if (Objects.nonNull(in)){
                try {
                    in.close();
                }catch (Exception e){
                    System.out.println(e);
                }
            }

            if (Objects.nonNull(out)){
                out.close();
                out=null;
            }

            if (Objects.nonNull(this.socket)){
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
