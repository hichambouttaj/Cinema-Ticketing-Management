package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entity.Actor;
import model.entity.Movie;
import model.impl.ActorDAOImp;

import java.net.URL;
import java.util.ResourceBundle;

public class ActorController implements Initializable {

    private ActorDAOImp actorDAOImp;

    @FXML
    private TextField roleField;
    @FXML
    private ComboBox<Actor> actorComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actorDAOImp = new ActorDAOImp();
        actorComboBox.setItems(actorDAOImp.getAll());
    }

    @FXML
    public Actor getNewActor(Movie movie){
        Actor actor = actorComboBox.getSelectionModel().getSelectedItem();
        if(actor == null){
            return null;
        }
        actor.setRole(roleField.getText());
        return actor;
    }
}
