package ru.itis.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import ru.itis.enums.Direction;
import ru.itis.models.Tank;
import ru.itis.sockets.ReceiveMessageTask;
import ru.itis.sockets.SocketClient;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {

    @FXML
    private AnchorPane pane;
//    @FXML
//    private Button btnStart;

    @FXML
    private Label tvName1;
    @FXML
    private Rectangle recTank1;
    @FXML
    private Ellipse elTower;
    @FXML
    private Rectangle recTrunk;
    private Tank player1;
    public static byte hp1 = 100;
    public String name1 = "player1";
    private Direction bodyDirection1 = Direction.RIGHT;
    private Direction towerDirection1 = Direction.RIGHT;

    @FXML
    private Label tvName2;

    public EventHandler<KeyEvent> keyEventEventHandler = keyEvent -> {

         player1 = new Tank(pane, recTank1, elTower, recTrunk, bodyDirection1, towerDirection1, name1, hp1);
         tvName1.setText(String.valueOf(player1.getHp()));
         if (player1.getHp() == 0)
             gameOver(player1);

        if (keyEvent.getCode() == KeyCode.A) {
            player1.moveLeft();
            bodyDirection1 = Direction.LEFT;
        } else if (keyEvent.getCode() == KeyCode.D) {
            player1.moveRight();
            bodyDirection1 = Direction.RIGHT;
        } else if (keyEvent.getCode() == KeyCode.W) {
            player1.moveUp();
            bodyDirection1 = Direction.UP;
        } else if (keyEvent.getCode() == KeyCode.S) {
            player1.moveDown();
            bodyDirection1 = Direction.DOWN;

        } else if (keyEvent.getCode() == KeyCode.I) {
            player1.rotateTower(Direction.UP);
            towerDirection1 = Direction.UP;
        } else if (keyEvent.getCode() == KeyCode.K) {
            player1.rotateTower(Direction.DOWN);
            towerDirection1 = Direction.DOWN;
        } else if (keyEvent.getCode() == KeyCode.J) {
            player1.rotateTower(Direction.LEFT);
            towerDirection1 = Direction.LEFT;
        } else if (keyEvent.getCode() == KeyCode.L) {
          player1.rotateTower(Direction.RIGHT);
            towerDirection1 = Direction.RIGHT;

        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            player1.shoot();
        }

        else if (keyEvent.getCode() == KeyCode.T) {
            player1.teleportToRight();
        } else if (keyEvent.getCode() == KeyCode.DELETE) {
            if (player1.suicide().get())
                hp1 = player1.getHp();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SocketClient client = new SocketClient("localhost", 1337);
        ReceiveMessageTask receiveMessageTask = new ReceiveMessageTask(client.getFromServer(), this);
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(receiveMessageTask);

//        btnStart.setFocusTraversable(false);
//        btnStart.setOnAction(actionEvent -> {
//            client.sendMessage("Client sent message");
//        });
    }

    public void gameOver(Tank wasted) {
        tvName1.setText("GAME OVER:\n" + wasted.getName() + "was killed");
    }
}