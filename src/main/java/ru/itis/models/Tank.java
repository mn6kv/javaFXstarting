package ru.itis.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import ru.itis.enums.Direction;

@AllArgsConstructor
public class Tank {

    @FXML
    private Pane pane;
    @FXML
    private Rectangle recTank1;
    @FXML
    private Ellipse elTower;
    @FXML
    private Rectangle recTrunk;

    private Direction mainDirection;
    private Direction towerDirection;

//    public Tank(Pane pane, Rectangle recTank1, Ellipse elTower, Rectangle recTrunk) {
//        this.pane = pane;
//        this.recTank1 = recTank1;
//        this.elTower = elTower;
//        this.recTrunk = recTrunk;
//    }

    public void moveLeft() {
        recTank1.setLayoutX(recTank1.getLayoutX() - 15);
        elTower.setLayoutX(elTower.getLayoutX() - 15);
        recTrunk.setLayoutX(recTrunk.getLayoutX() - 15);
        mainDirection = Direction.LEFT;
        this.horizontalyze(recTank1);
    }

    public void moveRight() {
        recTank1.setLayoutX(recTank1.getLayoutX() + 15);
        elTower.setLayoutX(elTower.getLayoutX() + 15);
        recTrunk.setLayoutX(recTrunk.getLayoutX() + 15);
        mainDirection = Direction.RIGHT;
        this.horizontalyze(recTank1);
    }

    public void moveUp() {
        recTank1.setLayoutY(recTank1.getLayoutY() - 15);
        elTower.setLayoutY(elTower.getLayoutY() - 15);
        recTrunk.setLayoutY(recTrunk.getLayoutY() - 15);
        mainDirection = Direction.UP;
        this.verticalize(recTank1);
    }

    public void moveDown() {
        recTank1.setLayoutY(recTank1.getLayoutY() + 15);
        elTower.setLayoutY(elTower.getLayoutY() + 15);
        recTrunk.setLayoutY(recTrunk.getLayoutY() + 15);
        this.verticalize(recTank1);
        towerDirection = Direction.DOWN;
    }

    public void shoot() {

        Circle bullet = new Circle(recTrunk.getLayoutX(), recTrunk.getLayoutY(), 5, Color.CHOCOLATE);
        pane.getChildren().add(bullet);

        if (towerDirection == Direction.RIGHT) {
            bullet.setLayoutX(bullet.getLayoutX() + 13);
            bullet.setLayoutY(bullet.getLayoutY() + 3);
        }
        if (towerDirection == Direction.LEFT) {
            bullet.setLayoutY(bullet.getLayoutY() + 3);
        }
        if (towerDirection == Direction.UP) {
            bullet.setLayoutX(bullet.getLayoutX() + 6);
        }
        if (towerDirection == Direction.DOWN) {
            bullet.setLayoutX(bullet.getLayoutX() + 6);
            bullet.setLayoutY(bullet.getLayoutY() + 13);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation -> {
            if (towerDirection == Direction.RIGHT)
                bullet.setLayoutX(bullet.getLayoutX() + 2);
            if (towerDirection == Direction.LEFT)
                bullet.setLayoutX(bullet.getLayoutX() - 2);
            if (towerDirection == Direction.UP)
                bullet.setLayoutY(bullet.getLayoutY() - 2);
            if (towerDirection == Direction.DOWN)
                bullet.setLayoutY(bullet.getLayoutY() + 2);
        }));

        timeline.setCycleCount(500);
        timeline.play();
    }

    public void verticalize(Shape item) {
        item.setRotate(90);
    }

    public void horizontalyze(Shape item) {
        item.setRotate(0);
    }

    public void verticalizeTower(Direction setTowerDirection) {
        elTower.setRotate(90);
        recTrunk.setRotate(90);

        if (towerDirection == Direction.RIGHT) {
            if (setTowerDirection == Direction.DOWN) {
                elTower.setLayoutX(elTower.getLayoutX() + 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() - 13);
                recTrunk.setLayoutY(recTrunk.getLayoutY() + 17);
            }
            if (setTowerDirection == Direction.UP) {
                elTower.setLayoutX(elTower.getLayoutX() + 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() - 13);
                recTrunk.setLayoutY(recTrunk.getLayoutY() - 18);
            }
        }

        if (towerDirection == Direction.LEFT) {
            if (setTowerDirection == Direction.UP) {
                elTower.setLayoutX(elTower.getLayoutX() + 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() + 18 + 6);
                recTrunk.setLayoutY(recTrunk.getLayoutY() - 18);
            }
            if (setTowerDirection == Direction.DOWN) {
                elTower.setLayoutX(elTower.getLayoutX() + 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() + 18 + 6);
                recTrunk.setLayoutY(recTrunk.getLayoutY() + 17);
            }
        }

        if (towerDirection == Direction.DOWN) {
            if (setTowerDirection == Direction.UP) {
                recTrunk.setLayoutY(recTrunk.getLayoutY() - 35);
            }
        }

        if (towerDirection == Direction.UP) {
            if (setTowerDirection == Direction.DOWN) {
                recTrunk.setLayoutY(recTrunk.getLayoutY() + 35);
            }
        }
    }

    public void horizontalyzeTower(Direction setTowerDirection) {
        elTower.setRotate(0);
        recTrunk.setRotate(0);

        if (towerDirection == Direction.DOWN) {
            if (setTowerDirection == Direction.RIGHT) {
                elTower.setLayoutX(elTower.getLayoutX() - 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() + 13);
                recTrunk.setLayoutY(recTrunk.getLayoutY() - 17);
            }
            if (setTowerDirection == Direction.LEFT) {
                elTower.setLayoutX(elTower.getLayoutX() - 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() - 18 - 6);
                recTrunk.setLayoutY(recTrunk.getLayoutY() - 17);
            }
        }

        if (towerDirection == Direction.UP) {
            if (setTowerDirection == Direction.RIGHT) {
                elTower.setLayoutX(elTower.getLayoutX() - 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() + 13);
                recTrunk.setLayoutY(recTrunk.getLayoutY() + 18);
            }
            if (setTowerDirection == Direction.LEFT) {
                elTower.setLayoutX(elTower.getLayoutX() - 6);
                recTrunk.setLayoutX(recTrunk.getLayoutX() - 18 - 6);
                recTrunk.setLayoutY(recTrunk.getLayoutY() + 18);
            }
        }

        if (towerDirection == Direction.RIGHT) {
            if (setTowerDirection == Direction.LEFT) {
                recTrunk.setLayoutX(recTrunk.getLayoutX() - 37);
            }
        }

        if (towerDirection == Direction.LEFT) {
            if (setTowerDirection == Direction.RIGHT) {
                recTrunk.setLayoutX(recTrunk.getLayoutX() + 37);
            }
        }
    }

    public void rotateTower(Direction direction) {
        if (direction == Direction.LEFT || direction == Direction.RIGHT)
            horizontalyzeTower(direction);
        if (direction == Direction.UP || direction == Direction.DOWN)
            verticalizeTower(direction);
        towerDirection = direction;
    }
}
