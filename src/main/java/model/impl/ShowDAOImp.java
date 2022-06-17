package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.IShowDAO;
import model.entity.Show;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class ShowDAOImp extends AbstractDAO implements IShowDAO {
    @Override
    public void add(Show obj) {
        int showId = obj.getShowId();
        LocalDate showDate = obj.getShowDate();
        String showStart = obj.getShowStart();
        String showEnd = obj.getShowEnd();
        double price = obj.getPrice();
        int movieId = obj.getMovieId();
        int salleId = obj.getSalleId();
        PreparedStatement pst = null;
        String sqlRequet = "insert into mydb.show (dateShow, startShow, endShow, price, Movie_idMovie, Salle_idSalle) values (?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setDate(1, Date.valueOf(showDate));
            pst.setString(2, showStart);
            pst.setString(3, showEnd);
            pst.setDouble(4, price);
            pst.setInt(5, movieId);
            pst.setInt(6, salleId);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        String sql = "delete from mydb.show where idShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Show getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show where idShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Show(resultSet.getInt(1), (resultSet.getDate(2)).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Show> getAll() {
        ObservableList<Show> showList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                showList.add(new Show(resultSet.getInt(1), (resultSet.getDate(2)).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return showList;
    }

    @Override
    public ObservableList<Show> getAll(int idMovie) {
        ObservableList<Show> showList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show where Movie_idMovie=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMovie);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                showList.add(new Show(resultSet.getInt(1), (resultSet.getDate(2)).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return showList;
    }

    @Override
    public void update(Show obj) {
        PreparedStatement preparedStatement = null;
        String sql = "update mydb.show set dateShow = ?, startShow = ?, endShow = ?, price=?, Salle_idSalle=? where mydb.show.idShow=? ";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(obj.getShowDate()));
            preparedStatement.setString(2, obj.getShowStart());
            preparedStatement.setString(3, obj.getShowEnd());
            preparedStatement.setDouble(4, obj.getPrice());
            preparedStatement.setInt(5, obj.getSalleId());
            preparedStatement.setInt(6, obj.getShowId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Show getShowBySalle(int idSalle) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show where Salle_idSalle=? limit 1";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSalle);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Show(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

//    public ObservableList<Show> getShowsBySalle(int idSalle){
//        ObservableList<Show> list = FXCollections.observableArrayList();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet;
//        String sql = "select * from mydb.show where Salle_idSalle=?";
//        try{
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, idSalle);
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while (resultSet.next()){
//                return resultSet.getInt(1);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return 0;
//    }
    @Override
    public int getSalleId(int idShow){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select Salle_idSalle from mydb.show where idShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idShow);
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

    @Override
    public ObservableList<Show> getShowBySalleByNow(int salleId){
        ObservableList<Show> showList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show where Salle_idSalle=? and dateShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salleId);
            LocalDate localDate = LocalDate.now();
            preparedStatement.setDate(2, Date.valueOf(localDate));
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                showList.add(new Show(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return showList;
    }

    @Override
    public Show getShow(int movieId, int salleId){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show where Movie_idMovie=? and Salle_idSalle=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, salleId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Show(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Integer> getShowByNow(){
        ObservableList<Integer> list = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select distinct Movie_idMovie from mydb.show where dateShow=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            LocalDate localDate = LocalDate.now();
            preparedStatement.setDate(1, Date.valueOf(localDate));
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
    public ObservableList<Show> getShows(LocalDate localDate, int idSalle) {
        ObservableList<Show> list = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from mydb.show where dateShow=? and Salle_idSalle=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(localDate));
            preparedStatement.setInt(2, idSalle);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                list.add(new Show(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
