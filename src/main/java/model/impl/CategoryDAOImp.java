package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dao.ICategoryDAO;
import model.entity.Actor;
import model.entity.Category;
import model.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDAOImp extends AbstractDAO implements ICategoryDAO {
    @Override
    public void add(Category obj) {
        String genre = obj.getGenre();
        PreparedStatement pst = null;
        String sqlRequet = "insert into mydb.category (genre) values (?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setString(1,genre);
            pst.execute();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed Add Category");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        String sql = "delete from mydb.category where idCategory=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Category getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from category where idCategory=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Category(resultSet.getInt(1), resultSet.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public ObservableList<Category> getAllByMovieId(int idMovie){
        ObservableList<Category> categoriesList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.category_has_movie where Movie_idMovie=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMovie);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                int categoryId = resultSet.getInt(1);
                categoriesList.add(getOne(categoryId));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return categoriesList;
    }
}
