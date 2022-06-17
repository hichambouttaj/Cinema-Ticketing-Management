package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.entity.Movie;
import model.entity.Salle;
import model.entity.Show;
import model.impl.SalleDAOImp;
import model.impl.ShowDAOImp;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowController implements Initializable {

    private SalleDAOImp salleDAOImp;
    @FXML
    private TextField startShowField;
    @FXML
    private DatePicker dateShowField;
    @FXML
    private TextField endShowField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<Salle> salleList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        salleDAOImp = new SalleDAOImp();;
        salleList.setItems(salleDAOImp.getAll());
        dateShowField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
    
    @FXML
    public Show getNewShow(Movie movie){
        Salle salle = salleList.getValue();
        return new Show(0, dateShowField.getValue(), startShowField.getText(), endShowField.getText(), Double.parseDouble(priceField.getText()), movie.getMovieId(), salle.getSalleId());
    }

    //set values dialog of show you want to edit
    @FXML
    public void editShow(Show show){
        dateShowField.setValue(show.getShowDate());
        startShowField.setText(show.getShowStart());
        endShowField.setText(show.getShowEnd());
        priceField.setText(String.valueOf(show.getPrice()));
        salleList.setValue(salleDAOImp.getOne(show.getSalleId()));
    }

    @FXML
    public void updateShow(Show show){
        show.setShowDate(dateShowField.getValue());
        show.setShowStart(startShowField.getText());
        show.setShowEnd(endShowField.getText());
        show.setPrice(Double.parseDouble(priceField.getText()));
        show.setSalleId(salleList.getValue().getSalleId());
    }

}
