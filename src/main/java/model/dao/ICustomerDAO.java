package model.dao;

import javafx.collections.ObservableList;
import model.entity.Customer;

public interface ICustomerDAO extends IDAO<Customer>{
    void update(Customer obj);
    ObservableList<Customer> find(String name);
    Customer find(String firstName, String lastName);
}
