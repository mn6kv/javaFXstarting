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

        tvName1.setText(String.valueOf(currentPlayer.getHp()));
        if (currentPlayer.getHp() == 0)
            gameOver(currentPlayer);
        tvName2.setText(String.valueOf(opponent.getHp()));
        if (opponent.getHp() == 0)
            gameOver(opponent);

        if (keyEvent.getCode() == KeyCode.A) {
            currentPlayer.moveLeft();
            bodyDirection1 = Direction.LEFT;
            client.setUserKey(KeyCode.A);
        } else if (keyEvent.getCode() == KeyCode.D) {
            currentPlayer.moveRight();
            bodyDirection1 = Direction.RIGHT;
            client.setUserKey(KeyCode.D);
        } else if (keyEvent.getCode() == KeyCode.W) {
            currentPlayer.moveUp();
            bodyDirection1 = Direction.UP;
            client.setUserKey(KeyCode.W);
        } else if (keyEvent.getCode() == KeyCode.S) {
            currentPlayer.moveDown();
            bodyDirection1 = Direction.DOWN;
            client.setUserKey(KeyCode.S);

        } else if (keyEvent.getCode() == KeyCode.I) {
            currentPlayer.rotateTower(Direction.UP);
            towerDirection1 = Direction.UP;
            client.setUserKey(KeyCode.I);
        } else if (keyEvent.getCode() == KeyCode.K) {
            currentPlayer.rotateTower(Direction.DOWN);
            towerDirection1 = Direction.DOWN;
            client.setUserKey(KeyCode.K);
        } else if (keyEvent.getCode() == KeyCode.J) {
            currentPlayer.rotateTower(Direction.LEFT);
            towerDirection1 = Direction.LEFT;
            client.setUserKey(KeyCode.J);
        } else if (keyEvent.getCode() == KeyCode.L) {
          currentPlayer.rotateTower(Direction.RIGHT);
            towerDirection1 = Direction.RIGHT;
            client.setUserKey(KeyCode.L);

        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            currentPlayer.shoot();
            hp2 -= 10;
            client.setUserKey(KeyCode.SPACE);
        }

        else if (keyEvent.getCode() == KeyCode.T) {
            currentPlayer.teleportToRight();
        } else if (keyEvent.getCode() == KeyCode.DELETE) {
            if (currentPlayer.suicide().get())
                hp1 = currentPlayer.getHp();
        }
    };

    public void opponentClick(String key) {

        KeyCode code = KeyCode.getKeyCode(key);
        renderTank();

        if (code == KeyCode.A) {
            opponent.moveLeft();
            bodyDirection2 = Direction.LEFT;
        } else if (code == KeyCode.D) {
            opponent.moveRight();
            bodyDirection2 = Direction.RIGHT;
        } else if (code == KeyCode.W) {
            opponent.moveUp();
            bodyDirection2 = Direction.UP;
        } else if (code == KeyCode.S) {
            opponent.moveDown();
            bodyDirection2 = Direction.DOWN;
        }

        else if (code == KeyCode.I) {
            opponent.rotateTower(Direction.UP);
            towerDirection2 = Direction.UP;
        } else if (code == KeyCode.K) {
            opponent.rotateTower(Direction.DOWN);
            towerDirection2 = Direction.DOWN;
        } else if (code == KeyCode.J) {
            opponent.rotateTower(Direction.LEFT);
            towerDirection2 = Direction.LEFT;
        } else if (code == KeyCode.L) {
            opponent.rotateTower(Direction.RIGHT);
            towerDirection2 = Direction.RIGHT;
        }

        else if (code == KeyCode.SPACE) {
            opponent.shoot();
            hp1 -= 10;
        }
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
    }

    public Byte getNumOfCurrentPlayer() {
        return numOfCurrentPlayer;
    }

    public void setNumOfCurrentPlayer(Byte numOfCurrentPlayer) {
        this.numOfCurrentPlayer = numOfCurrentPlayer;
    }
}