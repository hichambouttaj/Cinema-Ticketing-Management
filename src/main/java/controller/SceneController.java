package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneController {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void move(Scene scene){
        scene.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        Stage stage = (Stage)scene.getWindow();

        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
    }
    public static void changeScene(Stage stage, String viewName, String title){
        try {
            Parent root = FXMLLoader.load(SceneController.class.getResource("/view/" + viewName));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            move(scene);
            stage.setTitle(title);
            center(stage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void openNewScene(String viewName, String title){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(SceneController.class.getResource("/view/" + viewName));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            move(scene);
            stage.setTitle(title);
            stage.show();
            center(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //center scene to middle
    public static void center(Stage stage){
        Rectangle2D primaryScreen = Screen.getPrimary().getVisualBounds();
        stage.setX((primaryScreen.getWidth() - stage.getWidth()) / 2);
        stage.setY((primaryScreen.getHeight() - stage.getHeight()) / 2);
    }

}
