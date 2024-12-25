import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable {

    private ServerSocket serverSocket;
    private Server server;

    Connector(ServerSocket serverSocket, Server server) {
        this.serverSocket = serverSocket;
        this.server = server;
    }

    @Override
    public void run() {
        System.out.println("Waiting for client...");
        try {
            Socket socket = serverSocket.accept();
            server.addConnection(socket);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
