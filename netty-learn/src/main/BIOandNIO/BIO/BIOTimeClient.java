package BIOandNIO.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 * @Author: yzf
 * @Date: 2019-08-14 23:27
 * @Desoription:
 */
public class BIOTimeClient {
    public static void main(String[] args) {
        String query = "QUERY TIME ORDER";
        int port = 8080;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(query);
            System.out.println(query);
            String resp = in.readLine();
            System.out.println("NOW is : " + resp);
        } catch (Exception e) {

        }finally {
            if (Objects.nonNull(out)){
                out.close();
                out= null;
            }
            if (Objects.nonNull(in)){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (Objects.nonNull(socket)){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
