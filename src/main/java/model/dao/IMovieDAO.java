package model.dao;

import javafx.collections.ObservableList;
import model.entity.Movie;
import model.entity.Salle;
import model.entity.Show;

import java.util.List;

public interface IMovieDAO extends IDAO<Movie>{
    void update(Movie obj);
    ObservableList<Movie> find(String title);
    Movie getOne(String movieName);
}
