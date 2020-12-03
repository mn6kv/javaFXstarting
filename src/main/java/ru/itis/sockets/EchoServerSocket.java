package ru.itis.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 30.11.2020
 * 07. Sockets
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class EchoServerSocket {

    public void start(int port) {
        ServerSocket server;

        try {
            server = new ServerSocket(port);
            // уводит приложение в ожидание, пока не подключится клиент
            // как только клиент подключился, поток продолжает выполнение и помещает
            // "клиента" в client
            Socket client = server.accept();

            // читаем сообщения от клиента
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter toClient = new PrintWriter(client.getOutputStream(), true);

            String messageFromClient = fromClient.readLine();
            while (messageFromClient != null) {
                System.out.println("Message from client: " + messageFromClient);
                toClient.println("Message from server: " + messageFromClient);
                messageFromClient = fromClient.readLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}