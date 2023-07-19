package com.alhous.cras;

import com.alhous.cras.vue.Home;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        //var javaVersion = SystemInfo.javaVersion();
        // var javafxVersion = SystemInfo.javafxVersion();
        var home = new Home();

        var scene = new Scene(new StackPane(home), 1000, 500);
        stage.setTitle("CRAS 2023");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        // Set the dimensions of the window to the screen's dimensions
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
