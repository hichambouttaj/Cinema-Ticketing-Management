package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entity.*;
import model.impl.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTicketViewController implements Initializable {

    private MovieDAOImp movieDAOImp;
    private ShowDAOImp showDAOImp;
    private SalleDAOImp salleDAOImp;
    private SeatDAOImp seatDAOImp;
    private TicketDAOImp ticketDAOImp;
    private CustomerDAOImp customerDAOImp;
    @FXML
    private ComboBox<Seat> seatListComboBox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private CheckBox isSubsCheckBox;

    @FXML
    private Label finalPrice;

    @FXML
    private TextField cardNumberField;

    @FXML
    private DatePicker expDateCardField;

    @FXML
    private ComboBox<Show> showComboBox;

    @FXML
    private Label timeField;

    @FXML
    private Label salleName;

    @FXML
    private TextField emailField;

    @FXML
    private ImageView image;

    @FXML
    private VBox vBox;

    @FXML
    private TextField movieTitleField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        expDateCardField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        customerDAOImp = new CustomerDAOImp();
        ticketDAOImp = new TicketDAOImp();
        movieDAOImp = new MovieDAOImp();
        showDAOImp = new ShowDAOImp();
        salleDAOImp = new SalleDAOImp();
        seatDAOImp = new SeatDAOImp();
        ticketDAOImp = new TicketDAOImp();
        showComboBox.setOnAction(actionEvent -> {
            Show show = showComboBox.getSelectionModel().getSelectedItem();
            if(show != null){
                timeField.setText(show.getShowStart() + " To " + show.getShowEnd());
                Salle salle = salleDAOImp.getOne(show.getSalleId());
                salleName.setText(salle.getSalleName());
                ObservableList<Seat> seats = seatDAOImp.getAll(salle.getSalleId());
                seatListComboBox.setItems(seats);
                finalPrice.setText(show.getPrice() + "");
            }else{
                salleName.setText(null);
                timeField.setText(null);
                finalPrice.setText(null);
                seatListComboBox.setItems(null);
            }
        });

        //check subscription
        isSubsCheckBox.setOnAction(actionEvent -> {
            if(!isSubsCheckBox.isSelected()){
                finalPrice.setText(showComboBox.getSelectionModel().getSelectedItem().getPrice() + "");
                return;
            }
            Customer customer = null;
            if(firstNameField.getText() == null || lastNameField.getText() == null || firstNameField.getText().trim().equals("") || lastNameField.getText().trim().equals("")){
                return;
            }
            if(showComboBox.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            customer = customerDAOImp.find(firstNameField.getText(), lastNameField.getText());
            double price = showComboBox.getSelectionModel().getSelectedItem().getPrice();
            if(customer == null || customer.getSubsEnd().isBefore(LocalDate.now())){
                isSubsCheckBox.setSelected(false);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("You are not a Subs.");
                alert.showAndWait();
                finalPrice.setText(price + "");
                return;
            }
            finalPrice.setText((price - price * 0.2) + "");
        });
    }

    //search for movie by title
    @FXML
    public void searchBtnHandler(){
        if(movieTitleField == null){
            init();
            return;
        }
        if(movieTitleField.getText() == null){
            init();
            return;
        }
        if(movieTitleField.getText().trim().equals("")){
            init();
            return;
        }
        Movie movie = movieDAOImp.getOne(movieTitleField.getText());
        if(movie != null){
            movieTitleField.setText(movie.getTitle());
            String path = movie.getImage();
            if(path != null  && !path.trim().equals("")){
                image.setImage(new Image(path));
            }else{
                image.setImage(null);
            }
            ObservableList<Show> shows = showDAOImp.getAll(movie.getMovieId());
            Predicate<Show> predicate = new Predicate<Show>() {
                @Override
                public boolean test(Show show) {
                    return show.getShowDate().isAfter(LocalDate.now().minusDays(1));
                }
            };
            FilteredList<Show> filteredList = new FilteredList<>(shows, predicate);
            showComboBox.setItems(filteredList);
            isSubsCheckBox.setSelected(false);
            finalPrice.setText(null);
        }else{
            init();
        }
    }

    @FXML
    public void placeOrderHandler() throws FileNotFoundException, DocumentException {
        Seat seat = seatListComboBox.getSelectionModel().getSelectedItem();
        if((movieTitleField.getText() == null || movieTitleField.getText().trim() == "")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Movie Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the movie you want.");
            alert.showAndWait();
            return;
        }
        Show show = showComboBox.getSelectionModel().getSelectedItem();
        if(show == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Show Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the show you want.");
            alert.showAndWait();
            return;
        }
        if(seat == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Seat Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the seat you want.");
            alert.showAndWait();
            return;
        }

        List<Integer> list = ticketDAOImp.getAll(showComboBox.getSelectionModel().getSelectedItem().getShowId());
        for(int seatId : list){
            if(seatId == seat.getIdSeat()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong Seat");
                alert.setHeaderText(null);
                alert.setContentText("Seat already occuped");
                alert.showAndWait();
                return;
            }
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String cardNumber = cardNumberField.getText();
        //check input validation
        if(firstName == null || lastName == null || email == null || cardNumber == null || firstName.trim().equals("")|| lastName.trim().equals("")|| email.trim().equals("")|| cardNumber.trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No infomation insert");
            alert.setHeaderText(null);
            alert.setContentText("Please insert all information.");
            alert.showAndWait();
            return;
        }
        String regexName = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regexName);
        Matcher matcher = pattern.matcher(firstName);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please use correct firstName");
            alert.showAndWait();
            return;
        }
        matcher = pattern.matcher(lastName);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please use correct lastName");
            alert.showAndWait();
            return;
        }

        String regexEmail = "^(.+)@(\\S+)$";
        pattern = Pattern.compile(regexEmail);
        matcher = pattern.matcher(email);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email");
            alert.showAndWait();
            return;
        }

        Customer customer = customerDAOImp.find(firstName, lastName);

        if(cardNumberField.getText() == null || cardNumberField.getText().trim() == ""){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No CaredNumber");
            alert.setHeaderText(null);
            alert.setContentText("Please insert your card number.");
            alert.showAndWait();
            return;
        }

        String regexCardNumber = "[0-9]{16}";
        pattern = Pattern.compile(regexCardNumber);
        matcher = pattern.matcher(cardNumber);
        if(!matcher.matches()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid card Number");
            alert.showAndWait();
            return;
        }

        if(expDateCardField.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No exp date insert");
            alert.setHeaderText(null);
            alert.setContentText("Please insert your exp date.");
            alert.showAndWait();
            return;
        }
        String fullName = firstNameField.getText() + " " + lastNameField.getText();
        //generate ticket as pdf
        Stage stage = (Stage) vBox.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);
        if(file == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No path chosen");
            alert.setHeaderText(null);
            alert.setContentText("Please chose file you want to save ticket in");
            alert.showAndWait();
            return;
        }
        Document document = new Document();
        document.setPageSize(new Rectangle(400f, 200f));
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("""
                Full Name : %s
                Movie Title : %s 
                Show Date : %s Time : %s
                Salle : %s 
                Seat Number : %d
                """.formatted(fullName, movieTitleField.getText(), showComboBox.getSelectionModel().getSelectedItem().getShowDate().toString(), timeField.getText(), salleName.getText(), seatListComboBox.getSelectionModel().getSelectedItem().getSeatNumber())
        ));
        document.close();
        ticketDAOImp.add(new Ticket(0, showComboBox.getSelectionModel().getSelectedItem().getShowId(), cardNumber, fullName, showComboBox.getSelectionModel().getSelectedItem().getSalleId(),seatListComboBox.getSelectionModel().getSelectedItem().getIdSeat()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reservation Completed");
        alert.setHeaderText(null);
        alert.setContentText("Enjoy Your Show :D ♥");
        alert.showAndWait();
    }

    //change to user view
    @FXML
    public void goBackHandler() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        SceneController.changeScene(stage, "userView.fxml", "");
    }

    //initialize view
    @FXML
    public void init(){
        seatListComboBox.setItems(null);
        firstNameField.setText(null);
        lastNameField.setText(null);
        emailField.setText(null);
        isSubsCheckBox.setSelected(false);
        finalPrice.setText(null);
        cardNumberField.setText(null);
        expDateCardField.setValue(null);
        showComboBox.setItems(null);
        timeField.setText(null);
        movieTitleField.setText(null);
        salleName.setText(null);
        image.setImage(null);
    }

    @FXML
    public void setTicketDetails(Movie movie){
        movieTitleField.setText(movie.getTitle());
        image.setImage(new Image(movie.getImage()));
        ObservableList<Show> shows = showDAOImp.getAll(movie.getMovieId());
        showComboBox.setItems(shows);
    }
}
