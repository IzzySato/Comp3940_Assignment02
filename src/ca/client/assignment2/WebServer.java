package ca.client.assignment2;

import com.sun.net.httpserver.HttpServer;

import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class WebServer {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8086), 0);
        server.createContext("/", new HttpHandlerImpl());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("listening");

        //logger.info(" Server started on port 8001");

        //boolean listening = true;
//        ExecutorService threadPool = Executors.newCachedThreadPool();

//        try (ServerSocket serverSocket = new ServerSocket(8086)) {
//            System.out.println("Listening");
//            while (listening)
//                threadPool.execute(new WebServerThread(serverSocket.accept()));
//        } catch (Exception e) {
//            // Print error message
//            System.err.println("Error Creating Socket\n");
//            System.exit(-1);
//        }
    }
}
