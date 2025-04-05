package com.game.snake;

import java.util.Random;

public class Food {
    private int x;
    private int y;

    private Snake snake;

    public Food(int width, int height, Snake snake) {
        this.snake = snake;
        respawn(width, height);
    }

    public void respawn(int width, int height){
        Random random = new Random();
        int newX, newY;

        do {
            newX = random.nextInt(width);
            newY = random.nextInt(height);
        } while (snake.checkCollision(newX, newY));
        this.x = newX;
        this.y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
