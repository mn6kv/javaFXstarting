package ru.itis.sockets;

import javafx.concurrent.Task;
import ru.itis.controllers.MainController;

import java.io.BufferedReader;
import java.io.IOException;

public class ReceiveMessageTask extends Task<Void> {

    private BufferedReader fromServer;
    private MainController mainController;

    public ReceiveMessageTask(BufferedReader fromServer, MainController mainController) {
        this.fromServer = fromServer;
        this.mainController = mainController;
    }

    @Override
    protected Void call() throws Exception {
        String msgFromServer = null;
        while (true) {
            try {
                msgFromServer = fromServer.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            if (msgFromServer.equals("1") || msgFromServer.equals("2")) {
                mainController.setNumOfCurrentPlayer(Byte.valueOf(msgFromServer));
            } else if (msgFromServer != null) {
                System.out.println(msgFromServer);
                mainController.opponentClick(msgFromServer);
            }
        }
    }
}