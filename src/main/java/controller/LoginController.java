package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entity.User;
import model.impl.UserDAOImp;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class LoginController implements Initializable {
    private UserDAOImp userDAOImp;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setDisable(true);
        userDAOImp = new UserDAOImp();
    }


    @FXML
    public void keyPressedHandler(KeyEvent keyEvent){
        if(usernameField.getText().trim().equals("") || passwordField.getText().trim().equals("")){
            loginBtn.setDisable(true);
        } else{
            loginBtn.setDisable(usernameField.getText().charAt(0) == ' ' || passwordField.getText().charAt(0) == ' ');
        }
    }

    @FXML
    public void loginHandler() throws IOException {
        if(usernameField == null || passwordField == null){
            return;
        }
        Stage stage = (Stage)(anchorPane.getScene().getWindow());
        User user = userDAOImp.getOneByUserName(usernameField.getText());
        //check input validation
        if(user == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Information");
            alert.setHeaderText(null);
            alert.setContentText("Wrong username");
            alert.showAndWait();
            return;
        }
        if(user.getPassword().equals(passwordField.getText())){
            if(user.isAdmin()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(SceneController.class.getResource("/view/adminView.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                SceneController.move(scene);
                SceneController.center(stage);
                AdminViewController adminViewController = fxmlLoader.getController();
                adminViewController.setUsernameLabel(user.getUsername());
                return;
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(SceneController.class.getResource("/view/userView.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                SceneController.move(scene);
                SceneController.center(stage);
                UserViewController userViewController = fxmlLoader.getController();
                userViewController.setUsernameLabel(user.getUsername());
                return;
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Information");
            alert.setHeaderText(null);
            alert.setContentText("Wrong password");
            alert.showAndWait();
        }
    }
    @FXML
    public void closeHandler(){
        Platform.exit();
    }

    @FXML
    public void minimizeHandler(){
        Stage stage = (Stage)(anchorPane.getScene().getWindow());
        stage.setIconified(true);
    }

    @FXML
    public void forgetPassHandler(MouseEvent mouseEvent){

    }
    //change to sign up view
    @FXML
    public void SignUpHandler(ActionEvent actionEvent) {
        SceneController.openNewScene("registreView.fxml", "Sign Up");
    }

}
