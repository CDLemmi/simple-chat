import protocol.MessageInputStream;
import protocol.MessageOutputStream;
import static debug.Log.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



public class Client {

    private final String IP_ADDRESS = "127.0.0.1";
    private final int PORT = 4242;

    private Socket socket;

    private MessageOutputStream out;

    Client() {
        try {
            LogI("attempting to connect to server...");
            socket = new Socket(IP_ADDRESS, PORT);
            LogI("connected to server");

            MessageInputStream in = new MessageInputStream(socket.getInputStream());

            out = new MessageOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            boolean ready = false;
            while(!ready) {
                LogI("waiting for package in login phase");
                switch(in.read()[0]) {
                    case "REQUEST_PASSWORD" -> {
                        System.out.println("type in password");
                        String password = scanner.next();
                        out.write("SUPPLY_PASSWORD", password);
                        LogI("tried password: " + password);
                    }
                    case "REQUEST_NAME" -> {
                        System.out.println("type in name");
                        String name = scanner.next();
                        out.write("SUPPLY_NAME", name);
                        ready = true;
                        LogI("set name: " + name);
                    }
                    default -> throw new RuntimeException();
                }
            }

            Receiver receiver = new Receiver(in, this);
            Thread thread = new Thread(receiver);
            thread.start();

            System.out.println("You are now connected to the chat room");

            while(true) {
                String message = scanner.next();
                out.write("SEND_MESSAGE", message);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }







    public static void main(String[] args) {
        System.out.println("Hello Client");
        var client = new Client();
        System.out.println("program finished");
    }

}
