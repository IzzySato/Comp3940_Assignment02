package ca.client.assignment2;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;

public class FileUploadServlet extends HttpServlet{
    private static final String TEMPLATE_TOP = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "  <title>Document</title>\n" +
            "</head>\n" +
            "<body>\n";
    private static final String TEMPLATE_BOTTOM = "</body>\n" + "</html>";

    @Override
    public void doGet(HttpRequest req, HttpResponse res) throws IOException {
        System.out.println("Going to send response.");
        OutputStream outputStream = res.getResponseBody();
        String htmlBuilder =
                TEMPLATE_TOP +
                "  <h2>Upload Image</h2>\n" +
                "  <form action=\"http://localhost:8086\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
                "    <label for=\"file\">file:</label><br>\n" +
                "    <input type=\"file\" id=\"file\" name=\"file\"><br>\n" +
                "    <label for=\"caption\">caption:</label><br>\n" +
                "    <input type=\"text\" id=\"caption\" name=\"caption\"><br>\n" +
                "    <label for=\"date\">date:</label><br>\n" +
                "    <input type=\"date\" id=\"date\" name=\"date\"><br>\n" +
                "    <input type=\"submit\" id=\"submit\" name=\"submit\"><br>\n" +
                "  </form>\n" +
                TEMPLATE_BOTTOM;
        String htmlResponse = htmlBuilder;
        res.write(htmlResponse);
    }

    @Override
    public void doPost(HttpRequest req, HttpResponse res) throws IOException {
        //res.sendResponseHeaders(100, 0);
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(req.getRequestBody()));
        if (req.hasForm()) {
            req.getForm().keySet().forEach(key -> System.out.println("Form Key: " + key));
            MultiPartFormElement fileElement = req.getForm().get("file");
            String caption = req.getForm().get("caption").getValueAsString();
            String date = req.getForm().get("date").getValueAsString();
            String fileName = fileElement.getFileName();
            String ext = fileElement.getFileExtension();
            BufferedImage bImg = fileElement.getValueAsImage();
            String safeFileName = getSafeFileName( fileName, caption, date, ext );
            ImageUtil.saveImage(bImg, safeFileName, "image" );
        }

        File dir = new File("./image");
        String[] files = dir.list();
        String outResult = (req.isConsole()) ? getAsJSON(files) : getAsHTML(files);
        res.write(outResult);
    }

    private String getAsJSON( String[] files ) {
        // return JSON
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < files.length; i++){
            sb.append(files[i]);
            if (i < files.length-1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String getAsHTML( String[] files ) {
        StringBuilder sb = new StringBuilder();
        sb.append(TEMPLATE_TOP);
        sb.append("<h2>Files</h2>");
        sb.append("<ul>");
        for(int i = 0; i < files.length; i++){
            sb.append("<li>");
            sb.append(files[i]);
            sb.append("</li>");
        }
        sb.append("</ul>");
        sb.append(TEMPLATE_BOTTOM);
        return sb.toString();
    }

    private String getSafeFileName(String fileName, String caption, String date, String fileExt) throws UnsupportedEncodingException {
        return fileName
                + URLEncoder.encode(caption, StandardCharsets.UTF_8.toString())
                + URLEncoder.encode(date, StandardCharsets.UTF_8.toString())
                + "-"
                + String.valueOf(Clock.systemDefaultZone().millis())
                + "."
                + fileExt;
    }
}
