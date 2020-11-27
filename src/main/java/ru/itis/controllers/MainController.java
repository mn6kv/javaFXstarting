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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    private Boolean isPosHorisontal1 = true;

    public EventHandler<KeyEvent> keyEventEventHandler = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.A) {
            recTank1.setLayoutX(recTank1.getLayoutX() - 15);
            isPosHorisontal1 = true;
        } else if (keyEvent.getCode() == KeyCode.D) {
            recTank1.setLayoutX(recTank1.getLayoutX() + 15);
            isPosHorisontal1 = true;
        } else if (keyEvent.getCode() == KeyCode.W) {
            recTank1.setLayoutY(recTank1.getLayoutY() - 15);
            isPosHorisontal1 = false;
        } else if (keyEvent.getCode() == KeyCode.S) {
            recTank1.setLayoutY(recTank1.getLayoutY() + 15);
            isPosHorisontal1 = false;
        } else if (keyEvent.getCode() == KeyCode.CONTROL) {

            Circle bullet = new Circle(recTank1.getLayoutX(), recTank1.getLayoutY(), 5, Color.CHOCOLATE);
            pane.getChildren().add(bullet);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation -> {
                bullet.setLayoutX(bullet.getLayoutX() + 2);
            }));

            timeline.setCycleCount(500);
            timeline.play();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnStart.setOnAction(actionEvent -> {
            tvName.setText("Fight!");
        });
    }
}