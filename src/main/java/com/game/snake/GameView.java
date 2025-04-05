package com.game.snake;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GameView {
    private final Canvas canvas;
    private final int cellSize;
    private final GraphicsContext gc;
    private Label scoreLabel;
    private Button soundButton;
    private boolean soundOn = true;



    public GameView(int width, int height, int cellSize) {

        this.cellSize = cellSize;
        canvas = new Canvas(width * cellSize, height * cellSize);
        this.gc = canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return canvas;
    }



    public void render(Snake snake, Food food) {
        // Clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw apple
        gc.drawImage(GameResources.apple, food.getX() * cellSize-5, food.getY() * cellSize-5, 30, 30);

        // Draw snake
        boolean isHead = true;
        for (var segment : snake.getSegments()) {
            if (isHead) {
                gc.drawImage(GameResources.snakesHead, segment.x * cellSize - 2, segment.y * cellSize - 2, 24, 24);
                isHead = false;
            } else {
                gc.drawImage(GameResources.snakesBody, segment.x * cellSize, segment.y * cellSize, cellSize, cellSize);
            }
            /*double scale = 1.25; - Коэффициент увеличения
            double appleSize = cellSize * scale; - Новый размер яблока
            double offset = (appleSize - cellSize) / 2; - Смещение для центрирования*/
        }
    }

    public void showMessage(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    public HBox createHUD(GameController controller) {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        soundButton = new Button("🔊"); // Иконка динамика
        soundButton.setOnAction(event -> {
            toggleSound();
            soundButton.setFocusTraversable(false); // Убираем фокус с кнопки
            canvas.requestFocus(); // Вернуть фокус на игру
        });

        var restartButton = new Button("⟳"); // Иконка рестарта
        restartButton.setOnAction(e -> {
            controller.restartGame();
            restartButton.setFocusTraversable(false);
        });

        HBox hud = new HBox(15, scoreLabel, soundButton, restartButton);
        hud.setAlignment(Pos.CENTER);
        hud.setPadding(new Insets(10));
        hud.setStyle("-fx-background-color: darkgrey; -fx-padding: 5px;");
        return hud;
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    private void toggleSound() {
        soundOn = !soundOn;
        soundButton.setText(soundOn ? "🔊" : "🔇");
    }

    public boolean isSoundOn() {
        return soundOn;
    }
}
