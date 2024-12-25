package protocol;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static debug.Log.LogI;

public class MessageOutputStream {

    private static final byte SEPARATOR = 0x00;

    private OutputStream out;

    public MessageOutputStream(OutputStream out) {
        this.out = out;
    }

    public void write(String type) throws IOException {
        write(type, "");
    }

    public void write(String type, String content) throws IOException {
        byte[] typeBytes = type.getBytes(StandardCharsets.UTF_8);
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

        // Calculate the size of the resulting byte array
        byte[] result = new byte[typeBytes.length + 1 + contentBytes.length];

        // Copy the type bytes
        System.arraycopy(typeBytes, 0, result, 0, typeBytes.length);

        // Add the separator byte
        result[typeBytes.length] = SEPARATOR;

        // Copy the content bytes
        System.arraycopy(contentBytes, 0, result, typeBytes.length + 1, contentBytes.length);

        out.write(result);

        LogI(String.format("sent packet with type=%s; content=%s", type, content));
    }



}
