package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import model.entity.Customer;
import model.impl.CustomerDAOImp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class GestionSubsViewController implements Initializable {

    private CustomerDAOImp customerDAOImp;
    private ObservableList<Customer> observableList;
    private FilteredList<Customer> filteredList;

    private Predicate<Customer> wantCustomerEndSubsSoon;
    private Predicate<Customer> wantAllCustomer;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton addSubsHandler;

    @FXML
    private JFXCheckBox filterBtn;

    @FXML
    private TableView<Customer> listViewSubs;

    @FXML
    private JFXButton removeSubsHandler;

    @FXML
    private TextField searchBarSubs;

    @FXML
    private FontAwesomeIconView searchBtn;

    @FXML
    private JFXButton updateSubsHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDAOImp = new CustomerDAOImp();
        observableList = customerDAOImp.getAll();
        listViewSubs.setItems(observableList);
        //filter for subs date end soon (after 4 days)
        wantCustomerEndSubsSoon = customer -> customer.getSubsEnd().isBefore(LocalDate.now().plusDays(4)) && customer.getSubsEnd().isAfter(LocalDate.now().minusDays(1));
        wantAllCustomer = customer -> true;
        filteredList = new FilteredList<>(customerDAOImp.getAll(), wantCustomerEndSubsSoon);
    }

    @FXML
    void addSubsHandler(ActionEvent actionEvent){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Add New Subs");
        dialog.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/customerDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        ButtonType buttonOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            CustomerController contactController = fxmlLoader.getController();
            Customer customer = contactController.getNewCustomer();
            if(!checkInput(customer)){
                return;
            }
            customerDAOImp.add(customer);
            listViewSubs.setItems(customerDAOImp.getAll());
        }
    }

    //filter subs order by subs end
    @FXML
    void filterBySubsEndHandler(MouseEvent mouseEvent) {
        filteredList = new FilteredList<>(customerDAOImp.getAll(), wantCustomerEndSubsSoon);
        if(filterBtn.isSelected()){
            filteredList.setPredicate(wantCustomerEndSubsSoon);
        }else{
            filteredList.setPredicate(wantAllCustomer);
        }
        listViewSubs.setItems(filteredList);
    }

    @FXML
    void removeSubsHandler(ActionEvent event) {
        Customer selectedCustomer = listViewSubs.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Subs Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the subs you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Subs");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected Subs : " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            customerDAOImp.delete(selectedCustomer.getCustomerId());
            if(!filterBtn.isSelected()){
                listViewSubs.setItems(customerDAOImp.getAll());
            }else{
                filteredList = new FilteredList<>(customerDAOImp.getAll(), wantCustomerEndSubsSoon);
                listViewSubs.setItems(filteredList);
            }
        }
    }

    //search subs by firstName
    @FXML
    void searchSubsHandler(MouseEvent event) {
        String firstName = searchBarSubs.getText();
        listViewSubs.setItems(customerDAOImp.find(firstName));
    }

    @FXML
    void updateSubsHandler(ActionEvent event) {
        Customer selectedCustomer = listViewSubs.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Subs Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the subs you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Edit Subs");
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.initStyle(StageStyle.TRANSPARENT);
        fxmlLoader.setLocation(getClass().getResource("/view/customerDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        CustomerController contactController = fxmlLoader.getController();
        contactController.editCustomer(selectedCustomer);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(!checkInput(contactController.getNewCustomer())){
                return;
            }
            contactController.updateCustomer(selectedCustomer);
            customerDAOImp.update(selectedCustomer);
        }
    }

    //check input validation for subs
    private boolean checkInput(Customer customer){
        if(customer.getFirstName() == null || customer.getLastName() == null || customer.getAddress() == null || customer.getSubsEnd() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all information");
            alert.showAndWait();
            return false;
        }

        if(customer.getFirstName().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter firstName");
            alert.showAndWait();
            return false;
        }

        if(customer.getLastName().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter lastName");
            alert.showAndWait();
            return false;
        }

        if(customer.getAddress().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter address");
            alert.showAndWait();
            return false;
        }

        if(customer.getPhone().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter phone");
            alert.showAndWait();
            return false;
        }

        if(customer.getSubsEnd() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter subs End");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
