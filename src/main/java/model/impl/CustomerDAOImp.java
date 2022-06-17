package model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.ICustomerDAO;
import model.entity.Customer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerDAOImp extends AbstractDAO implements ICustomerDAO {
    @Override
    public void add(Customer obj) {
        LocalDate subsEnd = obj.getSubsEnd();
        String firstName = obj.getFirstName();
        String lastName = obj.getLastName();
        String phone = obj.getPhone();
        String address = obj.getAddress();
        PreparedStatement pst = null;
        String sqlRequet = "insert into customer (subsEnd, firstName, lastName, phone, address) values (?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setDate(1, Date.valueOf(subsEnd));
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, phone);
            pst.setString(5, address);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement pst = null;
        String sqlRequet = "delete from customer where idCustomer=?";
        try {
            pst = connection.prepareStatement(sqlRequet);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getOne(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from customer where idCustomer=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Customer(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "SELECT * FROM mydb.customer";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                customerList.add(new Customer(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public void update(Customer obj) {
        PreparedStatement preparedStatement = null;
        String sql = "update mydb.customer set subsEnd = ?, firstName = ?, lastName = ?, phone=?, address=? where mydb.customer.idCustomer=? ";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(obj.getSubsEnd()));
            preparedStatement.setString(2, obj.getFirstName());
            preparedStatement.setString(3, obj.getLastName());
            preparedStatement.setString(4, obj.getPhone());
            preparedStatement.setString(5, obj.getAddress());
            preparedStatement.setInt(6, obj.getCustomerId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Customer> find(String name) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "SELECT * FROM mydb.customer where firstName like ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, '%' + name + '%');
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                customerList.add(new Customer(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public Customer find(String firstName, String lastName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select * from customer where firstName=? and lastName=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Customer(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
