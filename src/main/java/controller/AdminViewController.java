package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/gestionMovieView.fxml"));
        anchorPane.getChildren().removeAll();
        try {
            anchorPane.getChildren().setAll((Node) fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void closeHandler(MouseEvent mouseEvent){
        Platform.exit();
    }

    @FXML
    public void minimizeHandler(MouseEvent mouseEvent){
        Stage stage = (Stage)(((Node)(mouseEvent.getSource())).getScene().getWindow());
        stage.setIconified(true);
    }

    //change to gestion movie view
    @FXML
    public void gestionMoviesViewHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/gestionMovieView.fxml"));
        anchorPane.getChildren().removeAll();
        anchorPane.getChildren().setAll((Node) fxmlLoader.load());
    }

    //change to gestion subs view
    @FXML
    public void gestionSubsViewHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/gestionSubsView.fxml"));
        anchorPane.getChildren().removeAll();
        anchorPane.getChildren().setAll((Node) fxmlLoader.load());
    }

    @FXML
    public void logoutHandler(){
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        SceneController.changeScene(stage, "login.fxml", "Login");
    }

    @FXML
    public void setUsernameLabel(String username){
        usernameLabel.setText(username);
    }
}
