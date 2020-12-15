package ru.itis.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private SocketClient client;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label tvName1;
    @FXML
    private Rectangle recTank1;
    @FXML
    private Ellipse elTower;
    @FXML
    private Rectangle recTrunk;

    private Tank currentPlayer;
    public static byte hp1 = 100;
    public String name1 = "player1";
    private Direction bodyDirection1 = Direction.RIGHT;
    private Direction towerDirection1 = Direction.RIGHT;

    @FXML
    private Label tvName2;
    @FXML
    private Rectangle recTank2;
    @FXML
    private Ellipse elTower2;
    @FXML
    private Rectangle recTrunk2;

    private Tank opponent;
    public static byte hp2 = 100;
    public String name2 = "player2";
    private Direction bodyDirection2 = Direction.RIGHT;
    private Direction towerDirection2 = Direction.RIGHT;

    private Byte numOfCurrentPlayer = null; // 1 - слева, 2 - справа, null - отсос

    public EventHandler<KeyEvent> keyEventEventHandler = keyEvent -> {

        renderTank();
        renderHealthPoints();

        if (keyEvent.getCode() == KeyCode.A) {
            currentPlayer.moveLeft();
            if (numOfCurrentPlayer == 1)
                bodyDirection1 = Direction.LEFT;
            else if (numOfCurrentPlayer == 2)
                bodyDirection2 = Direction.LEFT;
            client.setUserKey(KeyCode.A);
        } else if (keyEvent.getCode() == KeyCode.D) {
            currentPlayer.moveRight();
            if (numOfCurrentPlayer == 1)
                bodyDirection1 = Direction.RIGHT;
            else if (numOfCurrentPlayer == 2)
                bodyDirection2 = Direction.RIGHT;
            client.setUserKey(KeyCode.D);
        } else if (keyEvent.getCode() == KeyCode.W) {
            currentPlayer.moveUp();
            if (numOfCurrentPlayer == 1)
                bodyDirection1 = Direction.UP;
            else if (numOfCurrentPlayer == 2)
                bodyDirection2 = Direction.UP;
            client.setUserKey(KeyCode.W);
        } else if (keyEvent.getCode() == KeyCode.S) {
            currentPlayer.moveDown();
            if (numOfCurrentPlayer == 1)
                bodyDirection1 = Direction.DOWN;
            else if (numOfCurrentPlayer == 2)
                bodyDirection2 = Direction.DOWN;
            client.setUserKey(KeyCode.S);

        } else if (keyEvent.getCode() == KeyCode.I) {
            currentPlayer.rotateTower(Direction.UP);
            if (numOfCurrentPlayer == 1)
                towerDirection1 = Direction.UP;
            else if (numOfCurrentPlayer == 2)
                towerDirection2 = Direction.UP;
            client.setUserKey(KeyCode.I);
        } else if (keyEvent.getCode() == KeyCode.K) {
            currentPlayer.rotateTower(Direction.DOWN);
            if (numOfCurrentPlayer == 1)
                towerDirection1 = Direction.DOWN;
            else if (numOfCurrentPlayer == 2)
                towerDirection2 = Direction.DOWN;
            client.setUserKey(KeyCode.K);
        } else if (keyEvent.getCode() == KeyCode.J) {
            currentPlayer.rotateTower(Direction.LEFT);
            if (numOfCurrentPlayer == 1)
                towerDirection1 = Direction.LEFT;
            else if (numOfCurrentPlayer == 2)
                towerDirection2 = Direction.LEFT;
            client.setUserKey(KeyCode.J);
        } else if (keyEvent.getCode() == KeyCode.L) {
            currentPlayer.rotateTower(Direction.RIGHT);
            if (numOfCurrentPlayer == 1)
                towerDirection1 = Direction.RIGHT;
            else if (numOfCurrentPlayer == 2)
                towerDirection2 = Direction.RIGHT;
            client.setUserKey(KeyCode.L);

        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            currentPlayer.shoot(opponent, numOfCurrentPlayer);
            if (numOfCurrentPlayer == 1)
                hp2 = opponent.getHp();
            if (numOfCurrentPlayer == 2)
                hp1 = opponent.getHp();
            client.setUserKey(KeyCode.SPACE);
        } else if (keyEvent.getCode() == KeyCode.T) {
            currentPlayer.teleportToRight();
        } else if (keyEvent.getCode() == KeyCode.DELETE) {
            if (currentPlayer.suicide().get())
                hp1 = currentPlayer.getHp();
        }
        renderHealthPoints();
    };

    public void opponentClick(String key) {

        KeyCode code = KeyCode.getKeyCode(key);
        renderTank();
        renderHealthPoints();
        System.out.println("convertion in opponentClick(): " + code);

        if (code == KeyCode.A) {
            opponent.moveLeft();
            if (numOfCurrentPlayer == 1)
                bodyDirection2 = Direction.LEFT;
            else if (numOfCurrentPlayer == 2)
                bodyDirection1 = Direction.LEFT;

        } else if (code == KeyCode.D) {
            opponent.moveRight();
            if (numOfCurrentPlayer == 1)
                bodyDirection2 = Direction.RIGHT;
            else if (numOfCurrentPlayer == 2)
                bodyDirection1 = Direction.RIGHT;
        } else if (code == KeyCode.W) {
            opponent.moveUp();
            if (numOfCurrentPlayer == 1)
                bodyDirection2 = Direction.UP;
            else if (numOfCurrentPlayer == 2)
                bodyDirection1 = Direction.UP;
        } else if (code == KeyCode.S) {
            opponent.moveDown();
            if (numOfCurrentPlayer == 1)
                bodyDirection2 = Direction.DOWN;
            else if (numOfCurrentPlayer == 2)
                bodyDirection1 = Direction.DOWN;
        } else if (code == KeyCode.I) {
            opponent.rotateTower(Direction.UP);
            if (numOfCurrentPlayer == 1)
                towerDirection2 = Direction.UP;
            else if (numOfCurrentPlayer == 2)
                towerDirection1 = Direction.UP;
        } else if (code == KeyCode.K) {
            opponent.rotateTower(Direction.DOWN);
            if (numOfCurrentPlayer == 1)
                towerDirection2 = Direction.DOWN;
            else if (numOfCurrentPlayer == 2)
                towerDirection1 = Direction.DOWN;
        } else if (code == KeyCode.J) {
            opponent.rotateTower(Direction.LEFT);
            if (numOfCurrentPlayer == 1)
                towerDirection2 = Direction.LEFT;
            else if (numOfCurrentPlayer == 2)
                towerDirection1 = Direction.LEFT;
        } else if (code == KeyCode.L) {
            opponent.rotateTower(Direction.RIGHT);
            if (numOfCurrentPlayer == 1)
                towerDirection2 = Direction.RIGHT;
            else if (numOfCurrentPlayer == 2)
                towerDirection1 = Direction.RIGHT;
//        } else if (code == KeyCode.SPACE) { // вот так code = null, поэтому всем похер
        } else if (key.equals(KeyCode.SPACE.toString())) { //вот так после спейса они теряют связь (он будто выходит посреди метода shoot)
            if (numOfCurrentPlayer == 1) {
                opponent.shoot(currentPlayer, (byte) 2);
                hp1 = currentPlayer.getHp();
            } else if (numOfCurrentPlayer == 2) {
                opponent.shoot(currentPlayer, (byte) 1);
                hp2 = currentPlayer.getHp();
            }
        }
        renderHealthPoints();
    }

    private void renderTank() {
        if (numOfCurrentPlayer == 1) {
            currentPlayer = new Tank(pane, recTank1, elTower, recTrunk, bodyDirection1, towerDirection1, name1, hp1);
            opponent = new Tank(pane, recTank2, elTower2, recTrunk2, bodyDirection2, towerDirection2, name2, hp2);
        } else if (numOfCurrentPlayer == 2) {
            currentPlayer = new Tank(pane, recTank2, elTower2, recTrunk2, bodyDirection2, towerDirection2, name2, hp2);
            opponent = new Tank(pane, recTank1, elTower, recTrunk, bodyDirection1, towerDirection1, name1, hp1);
        }
    }

    private void renderHealthPoints() {
        if (numOfCurrentPlayer == 1) {
            tvName1.setText(name1 + ": " + String.valueOf(currentPlayer.getHp()) + " hp");
            tvName2.setText(name2 + ": " + String.valueOf(opponent.getHp()) + " hp");
        } else if (numOfCurrentPlayer == 2) {
            tvName1.setText(name1 + ": " + String.valueOf(opponent.getHp()) + " hp");
            tvName2.setText(name2 + ": " + String.valueOf(currentPlayer.getHp()) + " hp");
        }
        if (opponent.getHp() == 0)
            gameOver(opponent);
        if (currentPlayer.getHp() == 0)
            gameOver(currentPlayer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = new SocketClient("localhost", 1337);
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
        tvName2.setText("GAME OVER:\n" + wasted.getName() + "was killed");
    }

    public void setNumOfCurrentPlayer(Byte numOfCurrentPlayer) {
        this.numOfCurrentPlayer = numOfCurrentPlayer;
    }
}