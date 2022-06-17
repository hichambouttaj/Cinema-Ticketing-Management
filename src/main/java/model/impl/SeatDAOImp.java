package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.ISeatDAO;
import model.entity.Salle;
import model.entity.Seat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatDAOImp extends AbstractDAO implements ISeatDAO {
    @Override
    public void add(Seat obj) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Seat getOne(int id) {
        return null;
    }

    @Override
    public ObservableList<Seat> getAll() {
        return null;
    }

    @Override
    public ObservableList<Seat> getAll(int idSalle){
        ObservableList<Seat> seatList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.seat where Salle_idSalle=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSalle);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                seatList.add(new Seat(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return seatList;
    }

    @Override
    public int count(int idSalle){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select count(*) from mydb.seat where Salle_idSalle=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSalle);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

}
