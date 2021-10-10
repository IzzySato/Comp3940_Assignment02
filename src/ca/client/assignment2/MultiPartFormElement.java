package ca.client.assignment2;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;

public class MultiPartFormElement {
    private String name;
    private String file;
    private String fileExtension;
    private String fileName = "";
    private String contentType = "text";
    private byte[] body;
    MultiPartFormElement(String rawHeader, byte[] body) {
        String[] items = String.join(";", rawHeader.split("\r\n"))
                .split(HeaderType.VALUE_SEPARATOR);
        // We can ignore first. (Content-Disposition)
        for(int i=1; i < items.length; i++) {
            String item = items[i].trim();
            if( item.startsWith("name") ) {
                name = item.split("=")[1].replace("\"", "");
            } else if ( item.startsWith("filename")) {
                file = item.split("=")[1].replace("\"", "");
                parseFile();
            } else if ( item.startsWith("Content-Type")) {
                contentType = item.split(":")[1];
            }
        }
        this.body = body;
        System.out.println(this.toString());
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getValueAsString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    public String getFile() {
        return file;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public BufferedImage getValueAsImage() {
        return ImageUtil.fromBaseByteArray(body);
    }


    @Override
    public String toString() {
        return "FormElement{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                ", ext='" + fileExtension + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    private void parseFile(){
        int extensionStart = file.lastIndexOf(".");
        fileName = file.substring(0, extensionStart);
        fileExtension = file.substring(extensionStart + 1);
    }
}
