package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dao.IMovieDAO;
import model.entity.Movie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MovieDAOImp extends AbstractDAO implements IMovieDAO {
    @Override
    public void add(Movie obj) {
        String title = obj.getTitle();
        String director = obj.getDirector();
        String description = obj.getDescription();
        String image = obj.getImage();
        PreparedStatement pst = null;
        String sqlRequet = "insert into mydb.movie (title, director, description, image) values (?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setString(1, title);
            pst.setString(2, director);
            pst.setString(3, description);
            pst.setString(4, image);
            pst.execute();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed Add Movie");
            alert.setHeaderText(null);
            alert.setContentText("Please use other ID.");
            alert.showAndWait();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        String sql = "delete from movie where idMovie=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Movie getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from movie where idMovie=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Movie> getAll() {
        ObservableList<Movie> movieList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from movie";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                movieList.add(new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return movieList;
    }

    @Override
    public void update(Movie obj) {
        PreparedStatement preparedStatement = null;
        String sql = "update mydb.movie set title = ?, director = ?, description = ?, image=? where mydb.movie.idMovie=? ";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, obj.getTitle());
            preparedStatement.setString(2, obj.getDirector());
            preparedStatement.setString(3, obj.getDescription());
            preparedStatement.setString(4, obj.getImage());
            preparedStatement.setInt(5, obj.getMovieId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Movie> find(String title) {
        ObservableList<Movie> movieList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "SELECT * FROM mydb.movie where title like ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, '%' + title + '%');
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                movieList.add(new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return movieList;
    }

    @Override
    public Movie getOne(String movieName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from movie where title like ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, movieName + '%');
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
