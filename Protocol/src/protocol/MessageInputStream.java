package protocol;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static debug.Log.*;

public class MessageInputStream {

    private static final byte SEPARATOR = 0x00;

    private InputStream in;

    public MessageInputStream(InputStream in) {
        this.in = in;
    }


    public String read(String type) throws IOException {
        var s = read();
        assert(s[0].equals(type));
        return s[1];
    }

    public String[] read() throws IOException {
        byte[] buffer = new byte[1024];
        int length = in.read(buffer);
        assert(length != -1);
        byte[] data = new byte[length];
        System.arraycopy(buffer, 0, data, 0, length);

        int separatorIndex = -1;
        for(int i = 0; i < data.length; i++) {
            if (data[i] == SEPARATOR) {
                separatorIndex = i;
                break;
            }
        }
        if(separatorIndex ==-1) throw new IllegalArgumentException("Separator byte not found in data");

        // Extract the type bytes (before the separator)
        byte[] typeBytes = new byte[separatorIndex];
        System.arraycopy(data,0,typeBytes,0,separatorIndex);

        // Extract the content bytes (after the separator)
        byte[] contentBytes = new byte[data.length - separatorIndex - 1];
        System.arraycopy(data,separatorIndex +1,contentBytes,0,contentBytes.length);

        // Convert byte arrays back to strings
        String type = new String(typeBytes, StandardCharsets.UTF_8);
        String content = new String(contentBytes, StandardCharsets.UTF_8);

        LogI(String.format("received packet with type=%s; content=%s", type, content));

        // Return as a string array
        return new String[] {type, content};
    }
}
