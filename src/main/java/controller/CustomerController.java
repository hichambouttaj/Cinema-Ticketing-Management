package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.entity.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private TextField addressField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker subsEndField;
    @FXML
    private DialogPane dialogPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subsEndField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        subsEndField.setValue(LocalDate.now());
    }

    @FXML
    public Customer getNewCustomer(){
        Customer customer = new Customer(0, subsEndField.getValue(), firstNameField.getText(), lastNameField.getText(), phoneField.getText(), addressField.getText());
        return customer;
    }

    //set values of dialog to customer you want to edit
    @FXML
    public void editCustomer(Customer customer){
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
        subsEndField.setValue(customer.getSubsEnd());
    }

    @FXML
    public void updateCustomer(Customer customer){
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setPhone(phoneField.getText());
        customer.setAddress(addressField.getText());
        customer.setSubsEnd(subsEndField.getValue());
    }

}
