package com.game.snake;

import javafx.scene.image.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class GameResources {
    public static final Clip eatingSound = loadSound("/sounds/ate_apple.wav");
    public static final Clip victory = loadSound("/sounds/victory.wav");
    public static final Clip defeat = loadSound("/sounds/defeat.wav");

    public static final Image gameIcon = new Image(Objects.requireNonNull(GameResources.class.getResourceAsStream("/images/icon_snake.png"), "Icon image not found!"));
    public static final Image apple = new Image(Objects.requireNonNull(GameResources.class.getResourceAsStream("/images/apple.png"), "Apple image not found!"));
    public static final Image snakesHead = new Image(Objects.requireNonNull(GameResources.class.getResourceAsStream("/images/snake2.png"), "snake2 image not found!"));
    public static final Image snakesBody = new Image(Objects.requireNonNull(GameResources.class.getResourceAsStream("/images/snakebody.png"), "snake3 image not found!"));

    private static Clip loadSound(String path) {
        try (InputStream soundStream = GameResources.class.getResourceAsStream(path)) {
            if (soundStream == null) {
                throw new FileNotFoundException("Sound file not found: " + path);
            }

            // Копируем поток в ByteArrayInputStream, чтобы поддерживать операции mark/reset
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = soundStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Error loading sound: " + path, e);
        }
    }

    // Метод для воспроизведения звука
    public static void playSound(Clip sound) {
        if (sound != null) {
            sound.setFramePosition(0); // Перемещаем указатель на начало
            sound.start();
        }
    }
}

