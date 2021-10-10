package ca.client.assignment2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.MultipartStream;

public class MultiPartFormReader {
    private byte[] boundary;
    private Map<String, MultiPartFormElement> form = new HashMap<>();

    public MultiPartFormReader(InputStream input, String rawBoundary) {
        boundary = rawBoundary.getBytes(StandardCharsets.UTF_8);

        try {
            MultipartStream multipartStream = new MultipartStream(input, boundary);
            boolean nextPart = multipartStream.skipPreamble();
            OutputStream output = System.out;
            while(nextPart) {
                String header = multipartStream.readHeaders();
                ByteArrayOutputStream bsOut = new ByteArrayOutputStream(1024);
                multipartStream.readBodyData(bsOut);
                MultiPartFormElement element = new MultiPartFormElement(header, bsOut.toByteArray());
                form.put(element.getName(), element);
                nextPart = multipartStream.readBoundary();
            }
        } catch(MultipartStream.MalformedStreamException e) {
            // the stream failed to follow required syntax
        } catch(IOException e) {
            // a read or write error occurred
        }
    }

    public Map<String, MultiPartFormElement> getFormElements() {
        return form;
    }

}
