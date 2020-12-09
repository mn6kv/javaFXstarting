package ru.itis.sockets;

import java.io.IOException;

public class MainForServer {

    private static final int PORT = 1337;
    public static EchoServerSocket serverSocket = new EchoServerSocket();

    public static void main(String[] args) throws IOException {
        EchoServerSocket serverSocket = new EchoServerSocket();
        serverSocket.start(1337);

//        ServerSocket server = new ServerSocket(PORT);
//
//        while (true) {
//            Socket socket = server.accept();
//        }
    }
}
