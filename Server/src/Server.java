import debug.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    public final int PORT = 4242;

    private Scanner scanner;
    String password;

    private ServerSocket serverSocket;

    CopyOnWriteArrayList<Connection> connections = new CopyOnWriteArrayList<>();


    void handleMessage(String name, String message) {
        for(Connection connection : connections) {
            connection.sendMessage(String.format("%s: %s", name, message));
        }
    }


    void addConnection(Socket socket) {
        var connection = new Connection(socket, this);
        Thread thread = new Thread(connection, "Connection:" + connections.size());
        thread.start();
        connections.add(connection);
    }


    Server() {
        System.out.println("Simple Chat v0.1 started - Welcome!");
        System.out.print("set password for chatroom: ");
        scanner = new Scanner(System.in);
        password = scanner.next();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread connector = new Thread(new Connector(serverSocket, this), "Connector");
        connector.start();

    }

    public static void main(String[] args) {
        Log.applyArgs(args);
        var server = new Server();
    }

}
