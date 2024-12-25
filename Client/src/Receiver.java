import protocol.MessageInputStream;

import java.io.IOException;

public class Receiver implements Runnable {


    MessageInputStream in;
    Client client;

    Receiver(MessageInputStream in, Client client) {
        this.in = in;
        this.client = client;
    }


    @Override
    public void run() {
        while(true) {
            try {
                String message = in.read("SEND_MESSAGE");
                System.out.println(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
