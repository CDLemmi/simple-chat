import protocol.MessageInputStream;
import protocol.MessageOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static debug.Log.LogI;

public class Connection implements Runnable{

    Socket socket;
    MessageOutputStream out;
    MessageInputStream in;

    Server server;

    String name;

    Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }


    @Override
    public void run() {
        try {
            out = new MessageOutputStream(socket.getOutputStream());
            in = new MessageInputStream(socket.getInputStream());

            if(!authenticate(0)) {
                server.connections.remove(this);
                socket.close();
                return;
            }

            out.write("REQUEST_NAME");
            name = in.read("SUPPLY_NAME");

            while(true) {
                var content = in.read("SEND_MESSAGE");
                server.handleMessage(name, content);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void sendMessage(String message) {
        try {
            out.write("SEND_MESSAGE", message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean authenticate(int attempt) throws IOException {
        if(attempt >= 3) return false;
        out.write("REQUEST_PASSWORD");
        String password = in.read("SUPPLY_PASSWORD");
        boolean authenticated = server.password.equals(password);
        if(authenticated) {
            return true;
        } else {
            return authenticate(attempt+1);
        }

    }

}
