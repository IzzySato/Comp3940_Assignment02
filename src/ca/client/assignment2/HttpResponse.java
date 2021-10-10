package ca.client.assignment2;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class HttpResponse {
    HttpExchange exchange;
    public HttpResponse(HttpExchange httpExchange) {
        this.exchange = httpExchange;
    }

    public void setHeader(String key, String value){
        exchange.getResponseHeaders().put(key, Collections.singletonList(value));
    }

    public OutputStream getResponseBody() throws IOException {
        return exchange.getResponseBody();
    }

    public void write(String data) throws IOException {
        exchange.sendResponseHeaders(200, data.length());
        getResponseBody().write(data.getBytes());
        getResponseBody().flush();
    }

    public void sendResponseHeaders(int i, int i1) throws IOException {
        exchange.sendResponseHeaders(i, i1);
        getResponseBody().flush();
    }
}
