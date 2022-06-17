package model.dao;

import javafx.collections.ObservableList;
import model.entity.Seat;

public interface ISeatDAO extends IDAO<Seat>{
    ObservableList<Seat> getAll(int idSalle);
    int count(int idSalle);
}
