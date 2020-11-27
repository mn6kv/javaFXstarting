package ru.itis.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.controllers.MainController;

public class Main extends Application {

    private static final String fxmlFile = "/fxml/Main.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Tanks");
        stage.setResizable(false);

        Scene scene = stage.getScene();
        MainController mainController = fxmlLoader.getController();
        scene.setOnKeyPressed(mainController.keyEventEventHandler);

        stage.show();
    }
}
