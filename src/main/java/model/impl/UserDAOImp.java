package model.impl;

import model.dao.IUserDAO;
import model.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp extends AbstractDAO implements IUserDAO {
    @Override
    public void add(User obj) {
        int userId = obj.getUserId();
        String username = obj.getUsername();
        String email = obj.getEmail();
        String password = obj.getPassword();
        boolean isAdmin = obj.isAdmin();
        PreparedStatement pst = null;
        String sqlRequet = "insert into user (email, password, isAdmin, username) values (?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setBoolean(3, isAdmin);
            pst.setString(4, username);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        String sql = "delete from user where id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from user where id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), resultSet.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from user";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                userList.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), resultSet.getString(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getOneByUserName(String username){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from user where user.username like ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), resultSet.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
