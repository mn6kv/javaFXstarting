package ru.itis.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import ru.itis.controllers.MainController;
import ru.itis.enums.Direction;

import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class Tank {

    //    @FXML
    private Pane pane;
    //    @FXML
    private Rectangle recTank;
    //    @FXML
    private Ellipse elTower;
    //    @FXML
    private Rectangle recTrunk;

    private Direction mainDirection;
    private Direction towerDirection;

    private String name;
    private byte hp;

//    public Tank(Pane pane, Rectangle recTank1, Ellipse elTower, Rectangle recTrunk) {
//        this.pane = pane;
//        this.recTank1 = recTank1;
//        this.elTower = elTower;
//        this.recTrunk = recTrunk;
//    }

    public void moveLeft() {
        if (this.hp > 0) {
            recTank.setLayoutX(recTank.getLayoutX() - 15);
            elTower.setLayoutX(elTower.getLayoutX() - 15);
            recTrunk.setLayoutX(recTrunk.getLayoutX() - 15);
            mainDirection = Direction.LEFT;
            this.horizontalyze(recTank);
        }
    }

    public void moveRight() {
        if (this.hp > 0) {
            recTank.setLayoutX(recTank.getLayoutX() + 15);
            elTower.setLayoutX(elTower.getLayoutX() + 15);
            recTrunk.setLayoutX(recTrunk.getLayoutX() + 15);
            mainDirection = Direction.RIGHT;
            this.horizontalyze(recTank);
        }
    }

    public void moveUp() {
        if (this.hp > 0) {
            recTank.setLayoutY(recTank.getLayoutY() - 15);
            elTower.setLayoutY(elTower.getLayoutY() - 15);
            recTrunk.setLayoutY(recTrunk.getLayoutY() - 15);
            mainDirection = Direction.UP;
            this.verticalize(recTank);
        }
    }

    public void moveDown() {
        if (this.hp > 0) {
            recTank.setLayoutY(recTank.getLayoutY() + 15);
            elTower.setLayoutY(elTower.getLayoutY() + 15);
            recTrunk.setLayoutY(recTrunk.getLayoutY() + 15);
            this.verticalize(recTank);
            towerDirection = Direction.DOWN;
        }
    }

    //проверить на работе с противником
    public void shoot(Tank opponent, Byte numOfCurrentPlayer) {

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

        AtomicBoolean injured = new AtomicBoolean(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation -> {
            if (towerDirection == Direction.RIGHT)
                bullet.setLayoutX(bullet.getLayoutX() + 2);
            if (towerDirection == Direction.LEFT)
                bullet.setLayoutX(bullet.getLayoutX() - 2);
            if (towerDirection == Direction.UP)
                bullet.setLayoutY(bullet.getLayoutY() - 2);
            if (towerDirection == Direction.DOWN)
                bullet.setLayoutY(bullet.getLayoutY() + 2);

            if (opponent.isInjured(bullet)) {
                pane.getChildren().remove(bullet);
                injured.set(true);
                if (numOfCurrentPlayer == 1)
                    MainController.hp2 -= 10;
                else if (numOfCurrentPlayer == 2)
                    MainController.hp1 -=10;
                opponent.hp -= 10;
            }
        }));

        timeline.setCycleCount(500);
        timeline.play();

    }

    public AtomicBoolean suicide() {
        Circle bullet = new Circle(recTank.getLayoutX(), recTank.getLayoutY(), 5, Color.CHOCOLATE);
        pane.getChildren().add(bullet);

        bullet.setLayoutX(bullet.getLayoutX() - 100);
        bullet.setLayoutY(bullet.getLayoutY() + 10);
        AtomicBoolean injured = new AtomicBoolean(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation -> {
            bullet.setLayoutX(bullet.getLayoutX() + 2);
            if (this.isInjured(bullet)) {
                pane.getChildren().remove(bullet);
                injured.set(true);
                MainController.hp1 -= 10; //мини-костыль
            }
        }));

        timeline.setCycleCount(500);
        timeline.play();
        return injured;
    }

    public boolean isInjured(Circle enemyBullet) {
//        if ((enemyBullet.getCenterX() >= (this.recTank1.getX() - 1.7)) &&
//                (enemyBullet.getCenterY() >= (this.recTank1.getY() - 1.7)) &&
//                (enemyBullet.getCenterX() <= (this.recTank1.getX() +  + 50.7 + 1.7)) &&
//                (enemyBullet.getCenterY() <= (this.recTank1.getY() + 33 + 1.7)) &&
//                (enemyBullet.isVisible())) {
        if (enemyBullet.getBoundsInParent().intersects(recTank.getBoundsInParent()) &&
                (enemyBullet.isVisible())) {
            this.hp -= 10;
            System.out.println(hp);
            enemyBullet.setVisible(false);
            if (this.hp <= 0) {
                this.recTank.setFill(Color.RED);
                this.elTower.setFill(Color.BLACK);
                this.recTrunk.setFill(Color.BLACK);
            }
            return true;
        } else return false;
    }

    public void teleportToRight() {
        this.recTank.setLayoutX(recTank.getLayoutX() + 200);
        this.elTower.setLayoutX(elTower.getLayoutX() + 200);
        this.recTrunk.setLayoutX(recTrunk.getLayoutX() + 200);
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
        if (this.hp > 0) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT)
                horizontalyzeTower(direction);
            if (direction == Direction.UP || direction == Direction.DOWN)
                verticalizeTower(direction);
            towerDirection = direction;
        }
    }

    public byte getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }
}
