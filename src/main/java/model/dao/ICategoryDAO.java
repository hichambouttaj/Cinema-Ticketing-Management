package model.dao;

import javafx.collections.ObservableList;
import model.entity.Actor;
import model.entity.Category;

public interface ICategoryDAO extends IDAO<Category>{
    ObservableList<Category> getAllByMovieId(int idMovie);
}
