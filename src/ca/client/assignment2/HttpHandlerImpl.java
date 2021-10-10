package ca.client.assignment2;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.List;

public class HttpHandlerImpl implements com.sun.net.httpserver.HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws  IOException {
        System.out.println("Request received.");
        if ("GET".equals(httpExchange.getRequestMethod())) {
            System.out.println("Request is GET.");
            try {
                handleGetResponse(httpExchange);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            System.out.println("Request is POST.");
            try {
                handlePostResponse(httpExchange);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void logHeaders(HttpExchange exchange){
        Headers headers = exchange.getRequestHeaders();
        headers.keySet().forEach(key -> {
            List<String> values = headers.get(key);
            values.forEach(v -> System.out.println(key + " " + v));
        });
    }

    private String getHeader(String key, HttpExchange exchange){
        return exchange.getRequestHeaders().getFirst(key);
    }

    private HttpServlet getServlet() throws  IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = HttpHandlerImpl.class.getClassLoader();
        Class aClass = classLoader.loadClass("ca.client.assignment2.FileUploadServlet");
        return (HttpServlet) aClass.newInstance();
    }

    private void handleGetResponse(HttpExchange httpExchange)  throws  IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        OutputStream outputStream = httpExchange.getResponseBody();
        HttpServlet servlet = getServlet();
        servlet.doGet(new HttpRequest(httpExchange), new HttpResponse(httpExchange));
        outputStream.flush();
        outputStream.close();
    }

    private void handlePostResponse(HttpExchange httpExchange)  throws  IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        logHeaders(httpExchange);
        int contentLength = Integer.parseInt(getHeader("Content-length", httpExchange));
        OutputStream outputStream = httpExchange.getResponseBody();
        HttpServlet servlet = getServlet();
        servlet.doPost(new HttpRequest(httpExchange), new HttpResponse(httpExchange));
        httpExchange.sendResponseHeaders(201, contentLength);
        outputStream.flush();
        outputStream.close();
    }
}
