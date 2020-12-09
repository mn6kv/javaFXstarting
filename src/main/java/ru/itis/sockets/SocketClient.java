package ru.itis.sockets;

import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient {

    private Socket client;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private KeyCode userKey;
    private Byte numOfCurrentPlayer = null;
//    WriteMsg writeMsg = new WriteMsg();

    public SocketClient(String host, int port) {
        try {
            client = new Socket(host, port);
            toServer = new PrintWriter(client.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    public void sendMessage(String message) {
        toServer.println(message);
    }

    public BufferedReader getFromServer() {
        return fromServer;
    }

    public KeyCode getUserKey() {
        return userKey;
    }

    public void setUserKey(KeyCode userKey) {
        this.userKey = userKey;
        sendMessage(String.valueOf(userKey));
//        ExecutorService service = Executors.newFixedThreadPool(1);
//        service.execute(writeMsg);
    }

    public Byte getNumOfCurrentPlayer() {
        return numOfCurrentPlayer;
    }

    public void setNumOfCurrentPlayer(Byte numOfCurrentPlayer) {
        this.numOfCurrentPlayer = numOfCurrentPlayer;
    }
    //    public class WriteMsg extends Task<Void> {
//        @Override
//        public Void call() {
//            while (true) {
//                toServer.println(userKey);
//            }
//        }
//    }
}
