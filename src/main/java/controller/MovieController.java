package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.entity.Customer;
import model.entity.Movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MovieController implements Initializable {


    @FXML
    private TextField movieTitleField;
    @FXML
    private TextField directorField;
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField imagePathField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public Movie getNewMovie(){
        return new Movie(0, movieTitleField.getText(), directorField.getText(), descriptionField.getText(), imagePathField.getText());
    }

    //set values dialog of movie you want to edit
    @FXML
    public void editMovie(Movie movie){
        movieTitleField.setText(movie.getTitle());
        directorField.setText(movie.getDirector());
        descriptionField.setText(movie.getDescription());
        imagePathField.setText(movie.getImage());
    }

    @FXML
    public void updateMovie(Movie movie){
        movie.setTitle(movieTitleField.getText());
        movie.setDirector(directorField.getText());
        movie.setDescription(descriptionField.getText());
        movie.setImage(imagePathField.getText());
    }
}
