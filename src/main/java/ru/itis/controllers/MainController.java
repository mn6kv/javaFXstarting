package ru.itis.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    @FXML
    private Button btnStart;
    @FXML
    private Label tvName;

    @FXML
    private Rectangle recTank1;
    @FXML
    private Ellipse elTower;
    @FXML
    private Rectangle recTrunk;

    private Tank player;
    public static byte hp = 100;
    public String name = "player1";
    private Direction bodyDirection = Direction.RIGHT;
    private Direction towerDirection = Direction.RIGHT;

    public EventHandler<KeyEvent> keyEventEventHandler = keyEvent -> {

         player = new Tank(pane, recTank1, elTower, recTrunk, bodyDirection, towerDirection, name, hp);
         tvName.setText(String.valueOf(player.getHp()));
         if (player.getHp() == 0)
             gameOver(player);

        if (keyEvent.getCode() == KeyCode.A) {
            player.moveLeft();
            bodyDirection = Direction.LEFT;
        } else if (keyEvent.getCode() == KeyCode.D) {
            player.moveRight();
            bodyDirection = Direction.RIGHT;
        } else if (keyEvent.getCode() == KeyCode.W) {
            player.moveUp();
            bodyDirection = Direction.UP;
        } else if (keyEvent.getCode() == KeyCode.S) {
            player.moveDown();
            bodyDirection = Direction.DOWN;

        } else if (keyEvent.getCode() == KeyCode.I) {
            player.rotateTower(Direction.UP);
            towerDirection = Direction.UP;
        } else if (keyEvent.getCode() == KeyCode.K) {
            player.rotateTower(Direction.DOWN);
            towerDirection = Direction.DOWN;
        } else if (keyEvent.getCode() == KeyCode.J) {
            player.rotateTower(Direction.LEFT);
            towerDirection = Direction.LEFT;
        } else if (keyEvent.getCode() == KeyCode.L) {
          player.rotateTower(Direction.RIGHT);
            towerDirection = Direction.RIGHT;

        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            player.shoot();
        }

        else if (keyEvent.getCode() == KeyCode.T) {
            player.teleportToRight();
        } else if (keyEvent.getCode() == KeyCode.DELETE) {
            if (player.suicide().get())
                hp = player.getHp();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SocketClient client = new SocketClient("localhost", 1337);
        ReceiveMessageTask receiveMessageTask = new ReceiveMessageTask(client.getFromServer(), this);
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(receiveMessageTask);

        btnStart.setFocusTraversable(false);
        btnStart.setOnAction(actionEvent -> {
            tvName.setText("Fight!");
            client.sendMessage("Client sent message");
        });
    }

    public void gameOver(Tank wasted) {
        tvName.setText("GAME OVER:\n" + wasted.getName() + "was killed");
    }
}