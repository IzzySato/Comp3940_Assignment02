package ca.client.assignment2;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpRequest {
    HttpExchange exchange;
    private static final String MULTI_PART = "multipart/form-data";
    private static final String MULTI_PART_BOUNDARY_SPLIT = "=";
    private static final String POST = "POST";


    private int contentLength;
    private String contentType;
    private String requestMethod;
    private String formBoundaryKey = "";
    private String userAgent;
    private boolean hasForm = false;
    private Map<String, MultiPartFormElement> form = null;

    public HttpRequest(HttpExchange httpExchange) {
        this.exchange = httpExchange;
        requestMethod = exchange.getRequestMethod();
        userAgent = exchange.getRequestHeaders().getFirst(HeaderType.USER_AGENT);
        if(requestMethod.equals(POST)) {
            contentLength = Integer.parseInt(exchange.getRequestHeaders().getFirst(HeaderType.CONTENT_LENGTH));
            String rawContentType = exchange.getRequestHeaders()
                                            .getFirst(HeaderType.CONTENT_TYPE);
            contentType = rawContentType.split(HeaderType.VALUE_SEPARATOR)[0];
            if(contentType.equals(MULTI_PART)) {
                formBoundaryKey = rawContentType
                                            .split(HeaderType.VALUE_SEPARATOR)[1]
                                            .split(MULTI_PART_BOUNDARY_SPLIT)[1];
                MultiPartFormReader mpf = new MultiPartFormReader(exchange.getRequestBody(), formBoundaryKey);
                form = mpf.getFormElements();
                hasForm = true;
            }
        }
    }


    /**
     * @return String array of header name available
     */
    public String[] getHeaders(){
        return exchange.getRequestHeaders().keySet().toArray(new String[0]);
    }

    public int getContentLength() {
        return contentLength;
    }

    /**
     * @return String request method e.g. POST, GET
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    public InputStream getRequestBody() throws IOException {
        return exchange.getRequestBody();
    }

    public boolean hasForm() {
        return hasForm;
    }

    public Map<String, MultiPartFormElement> getForm() {
        return form;
    }

    public String getUserAgent(){
        return userAgent;
    }

    public boolean isConsole(){
        return userAgent.startsWith(HeaderType.USER_AGENT_CONSOLE);
    }
}
