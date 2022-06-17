package model.dao;

import javafx.collections.ObservableList;
import model.entity.Actor;

public interface IActorDAO extends IDAO<Actor>{
    ObservableList<Actor> getAllByMovieId(int idMovie);
    String getRole(int movieId, int actorId);
    void addActorToMovie(int idActor, int idMovie, String role);
}
