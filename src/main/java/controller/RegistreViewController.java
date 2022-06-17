package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.entity.User;
import model.impl.UserDAOImp;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistreViewController implements Initializable {

    private UserDAOImp userDAOImp;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    private JFXButton signUpBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpBtn.setDisable(true);
        userDAOImp = new UserDAOImp();
    }

    @FXML
    public void signUpHandler(ActionEvent actionEvent){
        //check input validation
        String regexUsername = "^[A-Za-z]\\w{5,29}$";
        Pattern pattern = Pattern.compile(regexUsername);
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        Matcher matcher = pattern.matcher(username);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please user only letters upper and lower case, numbers, underscore ...");
            alert.showAndWait();
            return;
        }
        String regexEmail = "^(.+)@(\\S+)$";
        pattern = Pattern.compile(regexEmail);
        matcher = pattern.matcher(email);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email");
            alert.showAndWait();
            return;
        }

        String regexPassword = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
        pattern = Pattern.compile(regexPassword);
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Weak password should contains at leat 8 characters, digits, uppercase, lowercase, special character");
            alert.showAndWait();
            return;
        }

        if(userDAOImp.getOneByUserName(username) != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Username already exist");
            alert.showAndWait();
            return;
        }
        userDAOImp.add(new User(0, email, password, false, username));
        ((Stage)(((Node)(actionEvent.getSource())).getScene().getWindow())).close();
    }

    @FXML
    public void keyPressedHandler(){
        if(usernameField.getText().trim().equals("") || emailField.getText().trim().equals("") ||
            passwordField.getText().trim().equals("")
        ){
            signUpBtn.setDisable(true);
        }else{
            signUpBtn.setDisable(usernameField.getText().charAt(0) == ' ' || emailField.getText().charAt(0) == ' ' ||
                    passwordField.getText().charAt(0) == ' ');
        }
    }

    @FXML
    public void closeHandler(MouseEvent mouseEvent){
        Stage stage = (Stage)(((Node)(mouseEvent.getSource())).getScene().getWindow());
        stage.close();
    }

    @FXML
    public void minimizeHandler(MouseEvent mouseEvent){
        Stage stage = (Stage)(((Node)(mouseEvent.getSource())).getScene().getWindow());
        stage.setIconified(true);
    }

}
