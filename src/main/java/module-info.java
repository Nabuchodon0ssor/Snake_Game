module com.game.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens com.game.snake to javafx.fxml;
    exports com.game.snake;
}