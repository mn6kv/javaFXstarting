package ru.itis.sockets;

import javafx.concurrent.Task;
import ru.itis.controllers.MainController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiveMessageTask extends Task<Void> {

    private BufferedReader fromServer;
    private MainController mainController;

    public ReceiveMessageTask(BufferedReader fromServer, MainController mainController) {
        this.fromServer = fromServer;
        this.mainController = mainController;
    }

    @Override
    protected Void call() throws Exception {
        while (true) {
            String msgFromServer = null;
            try {
                msgFromServer = fromServer.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            if (msgFromServer != null) {
                System.out.println(msgFromServer);
            }
        }
    }
}
