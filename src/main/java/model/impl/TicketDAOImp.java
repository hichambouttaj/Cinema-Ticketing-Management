package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dao.ITicketDAO;
import model.entity.Customer;
import model.entity.Seat;
import model.entity.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImp extends AbstractDAO implements ITicketDAO {
    @Override
    public void add(Ticket obj) {
        int idShow = obj.getIdShow();
        int idSeat = obj.getIdSeat();
        int idSalle = obj.getIdSalle();
        String creditCard = obj.getCreditCard();
        String fullName = obj.getFullName();
        PreparedStatement pst = null;
        String sqlRequet = "insert into mydb.ticket (Show_idShow, creditCard, fullName, seat_Salle_idSalle, seat_idSeat) values (?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setInt(1, idShow);
            pst.setString(2, creditCard);
            pst.setString(3, fullName);
            pst.setInt(4, idSalle);
            pst.setInt(5, idSeat);
            pst.execute();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed Add Ticket");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Ticket getOne(int id) {
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return null;
    }

    @Override
    public List<Integer> getAll(int showId){
        List<Integer> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "SELECT Seat_idSeat FROM mydb.ticket where Show_idShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, showId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                list.add(resultSet.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count(int showId){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "SELECT count(*) FROM mydb.ticket where Show_idShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, showId);
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
