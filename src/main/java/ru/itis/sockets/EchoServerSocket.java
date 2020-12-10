package ru.itis.sockets;

import javafx.concurrent.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerSocket extends Task<Void> {

    private ServerSocket server;
    private BufferedReader fromClient;
    private BufferedReader fromClient2;
    private PrintWriter toClient;
    private PrintWriter toClient2;
    private static final int PORT = 1337;
    private Socket client;
    private Socket client2;

//    public EchoServerSocket(Socket client) {
//        try {
//            this.client = client;
//            fromClient = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
//            toClient = new PrintWriter(this.client.getOutputStream(), true);
//            fromClient2 = new BufferedReader(new InputStreamReader(this.client2.getInputStream()));
//            toClient2 = new PrintWriter(this.client2.getOutputStream(), true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            Socket client = server.accept();
            Socket client2 = server.accept();
            System.out.println("clients ready");

            fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            toClient = new PrintWriter(client.getOutputStream(), true);
            fromClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
            toClient2 = new PrintWriter(client2.getOutputStream(), true);

            toClient.println("1");
            toClient2.println("2");

            new Thread(gameFor1).start();
            new Thread(gameFor2).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    Runnable gameFor1 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String messageFromClient = fromClient.readLine();
                    while (messageFromClient != null) {
                        toClient2.println(messageFromClient);
                        toClient2.flush();
                        messageFromClient = fromClient.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Runnable gameFor2 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String messageFromClient = fromClient2.readLine();
                    while (messageFromClient != null) {
                        toClient.println(messageFromClient);
                        toClient.flush();
                        messageFromClient = fromClient2.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected Void call() throws Exception {
        this.start(PORT);
        return null;
    }
}