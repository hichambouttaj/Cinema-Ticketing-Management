package model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entity.Movie;
import model.entity.Show;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public interface IShowDAO extends IDAO<Show>{

    ObservableList<Show> getAll(int idMovie);
    void update(Show obj);

    Show getShowBySalle(int idSalle);
    int getSalleId(int idShow);
    ObservableList<Show> getShowBySalleByNow(int salleId);
    Show getShow(int movieId, int salleId);
    ObservableList<Integer> getShowByNow();
    ObservableList<Show> getShows(LocalDate localDate, int idSalle);
}
