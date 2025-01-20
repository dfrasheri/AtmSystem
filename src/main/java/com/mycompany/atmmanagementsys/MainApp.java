package com.mycompany.atmmanagementsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load FXML with correct path
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);

        // Load CSS with correct path
        scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());

        // Set application icon
        Image icon = new Image("/icons/LoginPage.png");
        stage.getIcons().add(icon);

        // Set stage properties
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        // Play audio
        Media someSound = new Media(getClass().getResource("/audio/Welcome.mp3").toString());
        MediaPlayer mp = new MediaPlayer(someSound);
        mp.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
