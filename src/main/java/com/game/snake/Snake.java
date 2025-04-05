package com.game.snake;

import java.util.LinkedList;
import java.util.List;

public class Snake {

    private Food food;

    private LinkedList<Segment> snakeParts;
    private Direction direction;
    private int directionX = -1;
    private int directionY = 0;
    private boolean growing = false;
    private int foodEaten = 0;

    public Snake(int startX, int startY) {
        snakeParts = new LinkedList<>();
        snakeParts.add(new Segment(startX, startY));
        snakeParts.add(new Segment(startX+1, startY));
        snakeParts.add(new Segment(startX+2, startY));
        direction = Direction.LEFT;
    }

    public void move() {
        int newX = getHeadX() + directionX;
        int newY = getHeadY() + directionY;

        snakeParts.addFirst(new Segment(newX, newY));
        if (!growing) {
            snakeParts.removeLast();
        } else {
            growing = false;
        }
    }

    public void grow() {
        growing = true;
        foodEaten++;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public boolean isDead(int width, int height) {
        Segment head = snakeParts.getFirst();
        // Проверка выхода за границы
        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            return true;
        }
        // Проверка столкновения с собой
        for (int i = 1; i < snakeParts.size(); i++) {
            Segment seg = snakeParts.get(i);
            if (seg.x == head.x && seg.y == head.y) {
                return true;
            }
        }
        return false;
    }

    public void setDirection(Direction newDirection) {
        // Проверка на разворот на 180 градусов
        if ((newDirection == Direction.UP && directionY == 1) ||
                (newDirection == Direction.DOWN && directionY == -1) ||
                (newDirection == Direction.LEFT && directionX == 1) ||
                (newDirection == Direction.RIGHT && directionX == -1)) {
            return; // Игнорируем разворот
        }

        switch (newDirection) {
            case UP:    directionX = 0; directionY = -1; break;
            case DOWN:  directionX = 0; directionY = 1; break;
            case LEFT:  directionX = -1; directionY = 0; break;
            case RIGHT: directionX = 1; directionY = 0; break;
        }

        this.direction = newDirection;
    }

    public boolean checkCollision(int newX, int newY) {
        boolean collision = false;
        for(Segment segment : snakeParts){
            if(newX==segment.x && newY==segment.y) collision = true;
        }
        return collision;
    }

    public int getHeadX() {
        return snakeParts.getFirst().x;
    }

    public int getHeadY() {
        return snakeParts.getFirst().y;
    }

    public List<Segment> getSegments() {
        return snakeParts;
    }


    public static class Segment {
        public int x;
        public int y;

        public Segment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
