package ca.client.assignment2;

import java.io.PrintStream;
import java.net.Socket;

public class Client {

    public void connect(String server, int port){
        try {
            Socket socket = new Socket(server, port);
            PrintStream out = new PrintStream(socket.getOutputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

