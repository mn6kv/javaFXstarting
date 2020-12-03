package ru.itis.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TwoUsersSocket {

    private Socket client1;
    private Socket client2;

    public void start(int port) {
        ServerSocket server;
        try {
            server = new ServerSocket(port);
            client1 = server.accept();
            client2 = server.accept();

            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            PrintWriter toClient1 = new PrintWriter(client1.getOutputStream(), true);
            BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
            PrintWriter toClient2 = new PrintWriter(client2.getOutputStream(), true);

            String msgFromClient1 = fromClient1.readLine();
            String msgFromClient2 = fromClient2.readLine();

//            ReaderThread readerThread1 = new ReaderThread(fromClient1);
//            readerThread1.start();
//            String msgFromClient1 = readerThread1.msg;
//
//            ReaderThread readerThread2 = new ReaderThread(fromClient2);
//            readerThread2.start();
//            String msgFromClient2 = readerThread2.msg;

            while (msgFromClient1 != null || msgFromClient2 != null) {
                if (msgFromClient1 != null) {
                    System.out.println("FROM SERVER: message from client1: " + msgFromClient1);
                    toClient2.println("Client1 says: " + msgFromClient1);
                }
                if (msgFromClient2 != null) {
                    System.out.println("FROM SERVER: message from client2: " + msgFromClient2);
                    toClient1.println("Client2 says: " + msgFromClient2);
                }
                msgFromClient1 = fromClient1.readLine();
                msgFromClient2 = fromClient2.readLine();
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}