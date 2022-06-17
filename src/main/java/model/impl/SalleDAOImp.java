package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.dao.ISalleDAO;
import model.entity.Salle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalleDAOImp extends AbstractDAO implements ISalleDAO {

    @Override
    public void add(Salle obj) {
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public Salle getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.salle where idSalle=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Salle(resultSet.getInt(1), resultSet.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Salle> getAll() {
        ObservableList<Salle> salleList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.salle";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                salleList.add(new Salle(resultSet.getInt(1), resultSet.getString(2)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return salleList;
    }
}
