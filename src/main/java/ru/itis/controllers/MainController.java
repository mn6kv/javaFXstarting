package ru.itis.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import ru.itis.enums.Direction;
import ru.itis.models.Tank;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Direction bodyDirection = Direction.RIGHT;
    private Direction towerDirection = Direction.RIGHT;

    public EventHandler<KeyEvent> keyEventEventHandler = keyEvent -> {

         player = new Tank(pane, recTank1, elTower, recTrunk, bodyDirection, towerDirection);

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
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnStart.setFocusTraversable(false);
        btnStart.setOnAction(actionEvent -> {
            tvName.setText("Fight!");
        });
    }
}