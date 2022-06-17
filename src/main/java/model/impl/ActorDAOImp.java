package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dao.IActorDAO;
import model.entity.Actor;
import model.entity.Show;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActorDAOImp extends AbstractDAO implements IActorDAO {

    @Override
    public void add(Actor obj) {
        String fullName = obj.getFullName();
        PreparedStatement pst = null;
        String sqlRequet = "insert into mydb.actor (fullName) values (?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setString(1, fullName);
            pst.execute();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed Add Actor");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        String sql = "delete from mydb.actor where idActor=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Actor getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.actor where idActor=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Actor(resultSet.getInt(1), resultSet.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Actor> getAll() {
        ObservableList<Actor> actorList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.actor";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                actorList.add(new Actor(resultSet.getInt(1), resultSet.getString(2)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return actorList;
    }

    @Override
    public ObservableList<Actor> getAllByMovieId(int idMovie){
        ObservableList<Actor> actorList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.Actor_has_Movie where Movie_idMovie=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMovie);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                int actorId = resultSet.getInt(1);
                actorList.add(getOne(actorId));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return actorList;
    }

    @Override
    public String getRole(int movieId, int actorId){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select role from mydb.Actor_has_Movie where Movie_idMovie=? and Actor_idActor=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, actorId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return resultSet.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addActorToMovie(int idActor, int idMovie, String role){
        PreparedStatement pst = null;
        String sqlRequet = "insert into mydb.actor_has_movie values (?, ?, ?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setInt(1, idActor);
            pst.setInt(2, idMovie);
            pst.setString(3, role);
            pst.executeUpdate();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setContentText("Failed to Add Actor to Movie");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}
