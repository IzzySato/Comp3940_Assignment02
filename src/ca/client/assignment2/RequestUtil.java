package ca.client.assignment2;

public class RequestUtil {
    private static final int GET_SPACER = 4;
    private static final String GET = "GET ";
    private static final String HTTP = " HTTP/";
    private static final String USER = "User-Agent: ";
    private static final int USER_SPACER = 12;
    private static final int START_SPACER = 7;

    public static String getPath(String request) {
        int start = request.indexOf(GET) + GET_SPACER;
        int end = request.indexOf(HTTP);
        request = request.substring(start, end);
        return request;
    }

    public static String getUserAgent(String request) {
        int start = request.indexOf(USER) + USER_SPACER;
        request = request.substring(start, start + START_SPACER);
        return request;
    }

}
