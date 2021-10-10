package ca.client.assignment2;

import java.net.*;
import java.io.*;

public class WebServerThread extends Thread {
    private Socket socket = null;
    private boolean isConsole;
    private String method = "GET";

    public WebServerThread(Socket socket) {
        this.socket = socket;
        HttpServlet http = new FileUploadServlet();
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(
                     socket.getInputStream()));
        ) {
            Writer fileWriter = new FileWriter("test.txt");
            String inputLine;
            String request = "";
            String path = "";
            String userAgent = "";

            while ((inputLine = in.readLine()) != null) {

                request += inputLine + "\n";
                if(request.contains("POST")) {
                    method = "POST";
                }
                System.out.println(inputLine);
                fileWriter.write(inputLine + "\n");
                if (path.equals("") && request.contains("GET")) {
                    path = RequestUtil.getPath(request);
                }

                if (request.contains("User-Agent")) {
                    userAgent = RequestUtil.getUserAgent(request);

                    isConsole = userAgent.equals("Console");
                    break;
                }

            }
            if(method.equals("GET")) {
                System.out.println("sending");
                String topPart = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"UTF-8\">\n" +
                        "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "  <title>Document</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "  <h2>Upload Image</h2>\n" +
                        "  <form action=\"http://localhost:8086\" method=\"POST\">\n" +
                        "    <label for=\"file\">file:</label><br>\n" +
                        "    <input type=\"file\" id=\"file\" name=\"file\"><br>\n" +
                        "    <label for=\"caption\">caption:</label><br>\n" +
                        "    <input type=\"text\" id=\"caption\" name=\"caption\"><br>\n" +
                        "    <label for=\"date\">date:</label><br>\n" +
                        "    <input type=\"date\" id=\"date\" name=\"date\"><br>\n" +
                        "    <input type=\"submit\" id=\"submit\" name=\"submit\"><br>\n" +
                        "  </form>\n" +
                        "</body>\n" +
                        "</html>";
                out.write("HTTP/1.0 200 OK\n");
                out.flush();
                out.write("Content-Type: text/html\n\n");
                out.flush();
                out.write(topPart);
                out.flush();
            }
            // Close socket
            fileWriter.close();
            socket.close();
        } catch (Exception e) {
            // Print error message.
            System.out.println("Exception in thread: " + Thread.currentThread().getId() + "\nMessage: " + e.getMessage() + "\n");
            Thread.currentThread().interrupt();
        }
    }
}
