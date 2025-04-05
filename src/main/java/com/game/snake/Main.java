package com.game.snake;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        gameController = new GameController(20, 20, 20);
        gameController.initialize(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
