package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entity.Movie;
import model.entity.Show;
import model.impl.MovieDAOImp;
import model.impl.ShowDAOImp;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ShowNowController implements Initializable {

    private ShowDAOImp showDAOImp;
    private MovieDAOImp movieDAOImp;
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Show> shows = FXCollections.observableArrayList();

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableView<Show> showTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showDAOImp = new ShowDAOImp();
        movieDAOImp = new MovieDAOImp();
        for(int idMovie : showDAOImp.getShowByNow()){
            movies.add(movieDAOImp.getOne(idMovie));
        }
        movieTableView.setItems(movies);
        movieTableView.setOnMouseClicked(mouseEvent -> {
            Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
            if(selectedMovie != null){
                shows = showDAOImp.getAll(selectedMovie.getMovieId());
                Predicate<Show> predicate = show -> show.getShowDate().isEqual(LocalDate.now());
                for(Show show : shows){
                    if(!predicate.test(show)){
                        shows.remove(show);
                    }
                }
                showTableView.setItems(shows);
            }else{
                showTableView.setItems(null);
            }
        });
    }
}
