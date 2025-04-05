package com.game.snake;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;


public class GameController {

    private int width;
    private int height;
    private int cellSize;
    private long speed = 200_000_000;

    private Snake snake;
    private Food food;
    private GameView gameView;
    private VBox root;

    private AnimationTimer gameLoop;
    private boolean isPaused = false;
    private int score = 0;


    public GameController(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
    }

    public void initialize(Stage stage) {
        gameView = new GameView(width, height, cellSize);
        HBox hud = gameView.createHUD(this);

        snake = new Snake(width / 2, height / 2);
        food = new Food(width, height, snake);

        // Добавляем `HUD` наверх окна
        root = new VBox(hud, gameView.getCanvas());
        // Создаём сцену и вешаем обработчик клавиш
        Scene scene = new Scene(root, width * cellSize, height * cellSize+37.5);
        initKeyboard(scene);
        scene.setOnMouseClicked(event -> {
            scene.getRoot().requestFocus(); // Переносим фокус обратно на сцену
        });

        stage.setScene(scene);
        root.requestFocus();
        stage.getIcons().add(GameResources.gameIcon);
        stage.setTitle("Snake Game");
        startGameLoop();
    }

    private void startGameLoop() {

        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= speed) {
                    update();
                    lastUpdate = now;
                }
            }
        };

        gameLoop.start();
    }

    private void update() {
        if (isPaused) return;
        snake.move();

        if (snake.getHeadX() == food.getX() && snake.getHeadY() == food.getY()) {
            snake.grow();
            score+=10;
            if (gameView.isSoundOn()) {
                GameResources.playSound(GameResources.eatingSound);
            }
            gameView.updateScore(score);
            food.respawn(width, height);
            if (snake.getFoodEaten() % 5 == 0) {
                speed *= 0.9;
            }
        }

        if (snake.isDead(width, height)) {
            gameOver();
        }
        if (snake.getFoodEaten() >= 30) {
            win();
        }
        gameView.render(snake, food);
    }

    private void gameOver() {
        gameLoop.stop();
        gameView.showMessage("" ,"Game Over");
        if (gameView.isSoundOn()) {
            GameResources.playSound(GameResources.defeat);
        }

    }

    private void win(){
        gameLoop.stop();
        gameView.showMessage("" ,"You Won!");
        if (gameView.isSoundOn()) {
            GameResources.playSound(GameResources.victory);
        }
    }

    private void initKeyboard(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    snake.setDirection(Direction.UP);
                    break;
                case DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case SPACE:
                    isPaused = !isPaused;
                    break;
            }
        });
    }

    public void restartGame() {
        gameLoop.stop();

        // Сбрасываем данные
        snake = new Snake(width / 2, height / 2);
        food = new Food(width, height, snake);
        score = 0;
        speed = 200_000_000;
        gameView.updateScore(score);


        startGameLoop();
        gameView.render(snake, food);
        root.requestFocus();
    }
}
