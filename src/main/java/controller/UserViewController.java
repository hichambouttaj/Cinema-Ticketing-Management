package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entity.Movie;
import model.entity.Salle;
import model.entity.Show;
import model.impl.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    private Movie selMovie = null;
    private SalleDAOImp salleDAOImp;
    private ShowDAOImp showDAOImp;
    private MovieDAOImp movieDAOImp;
    private SeatDAOImp seatDAOImp;
    private TicketDAOImp ticketDAOImp;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label usernameLabel;
    @FXML
    private VBox vBox;
    @FXML
    private HBox miniCardBox;
    @FXML
    private ComboBox<Salle> salleComboBox;
    @FXML
    private Label descriptionField;
    @FXML
    private Label dateField;
    @FXML
    private Label timeShow;
    @FXML
    private Label availableSeatField;

    @FXML
    private ImageView image;

    @FXML
    private Label movieLabel;

    @FXML
    private ComboBox<Movie> movieTitleComboxBox;
    private List<Movie> recentlyAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieTitleComboxBox.setVisible(false);
        movieLabel.setVisible(false);
        descriptionField.setText("");
        dateField.setText("");
        timeShow.setText("");
        availableSeatField.setText("");
        seatDAOImp = new SeatDAOImp();
        ticketDAOImp = new TicketDAOImp();
        movieDAOImp = new MovieDAOImp();
        showDAOImp = new ShowDAOImp();
        salleDAOImp = new SalleDAOImp();
        recentlyAdded = new ArrayList<>();
        recentlyAdded = recentlyAdded();
        //find movie show by now in salle
        salleComboBox.setOnAction(actionEvent -> {
            Salle salle = salleComboBox.getSelectionModel().getSelectedItem();
            if(salle != null){
                ObservableList<Show> showList = showDAOImp.getShowBySalleByNow(salle.getSalleId());
                if(showList.isEmpty()){
                    movieTitleComboxBox.setVisible(false);
                    movieLabel.setVisible(false);
                    descriptionField.setText("");
                    dateField.setText("");
                    timeShow.setText("");
                    availableSeatField.setText("");
                    image.setImage(null);
                    selMovie = null;
                    return;
                }
                if(showList.size() == 1){
                    movieTitleComboxBox.setVisible(false);
                    movieLabel.setVisible(false);
                    Show show = showList.get(0);
                    int movieId = show.getMovieId();
                    Movie movie = movieDAOImp.getOne(movieId);
                    descriptionField.setText(movie.getDescription());
                    dateField.setText(show.getShowDate().toString());
                    timeShow.setText(show.getShowStart() + " - " + show.getShowEnd());
                    selMovie = movie;
                    image.setImage(new Image(selMovie.getImage()));
                    int nbrSeat = seatDAOImp.count(show.getSalleId()) - ticketDAOImp.count(show.getShowId());
                    availableSeatField.setText(nbrSeat + "");
                    return;
                }
                if(showList.size() > 1) {
                    movieLabel.setVisible(true);
                    movieTitleComboxBox.setVisible(true);
                    ObservableList<Movie> movieList = FXCollections.observableArrayList();
                    for(int i = 0; i < showList.size(); i++){
                        Show show = showList.get(i);
                        movieList.add(movieDAOImp.getOne(show.getMovieId()));
                    }
                    movieTitleComboxBox.setItems(movieList);
                    movieTitleComboxBox.setOnAction(actionEvent1 -> {
                        Movie movie = movieTitleComboxBox.getSelectionModel().getSelectedItem();
                        if(movie != null){
                            Show show = showDAOImp.getShow(movie.getMovieId(), salle.getSalleId());
                            descriptionField.setText(movie.getDescription());
                            dateField.setText(show.getShowDate().toString());
                            timeShow.setText(show.getShowStart() + " - " + show.getShowEnd());
                            selMovie = movie;
                            image.setImage(new Image(selMovie.getImage()));
                            int nbrSeat = seatDAOImp.count(show.getSalleId()) - ticketDAOImp.count(show.getShowId());
                            availableSeatField.setText(nbrSeat + "");
                        }
                    });
                }
            }
        });

        //recently show
        ObservableList<Salle> list = FXCollections.observableArrayList();
        list = salleDAOImp.getAll();
        salleComboBox.setItems(list);
        try {
            for(int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/miniCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                miniCardBox.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<Movie> recentlyAdded(){
        ObservableList<Integer> list = showDAOImp.getShowByNow();
        ObservableList<Movie> movieList = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++){
            Movie movie = movieDAOImp.getOne(list.get(i));
            if(!movieList.contains(movie)){
                movieList.add(movieDAOImp.getOne(list.get(i)));
            }
        }
        return movieList;
    }

    @FXML
    public void closeHandler(){
        Platform.exit();
    }

    @FXML
    public void minimizeHandler(MouseEvent mouseEvent){
        Stage stage = (Stage)(((Node)(mouseEvent.getSource())).getScene().getWindow());
        stage.setIconified(true);
    }

    @FXML
    public void logoutHandler(){
        Stage stage = (Stage)borderPane.getScene().getWindow();
        SceneController.changeScene(stage, "login.fxml", "Login");
    }

    @FXML
    public void moviesViewHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/moviesView.fxml"));
        vBox.getChildren().removeAll();
        vBox.getChildren().setAll((Node) fxmlLoader.load());
        MovieViewController movieViewController = fxmlLoader.getController();
        if(movieViewController != null){
            if(selMovie != null){
                movieViewController.setMovieDetails(selMovie);
            }
        }
    }

    @FXML
    public void ticketViewHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/addTicketView.fxml"));
        vBox.getChildren().removeAll();
        vBox.getChildren().setAll((Node) fxmlLoader.load());
        AddTicketViewController addTicketViewController = fxmlLoader.getController();
        if(addTicketViewController != null){
            if(selMovie != null){
                    addTicketViewController.setTicketDetails(selMovie);
            }
        }
    }

    @FXML
    public void homeViewHandler() throws IOException {
        Stage stage = (Stage)borderPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(SceneController.class.getResource("/view/userView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        SceneController.move(scene);
        UserViewController userViewController = fxmlLoader.getController();
        userViewController.setUsernameLabel(usernameLabel.getText());
    }

    @FXML
    public void getTicketBtnHandler() throws IOException {
        ticketViewHandler();
    }

    @FXML
    public void moreInfoBtnHandler() throws IOException {
        moviesViewHandler();
    }

    @FXML
    public void setUsernameLabel(String username){
        usernameLabel.setText(username);
    }

    @FXML
    public void showViewHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/showNowView.fxml"));
        vBox.getChildren().removeAll();
        vBox.getChildren().setAll((Node) fxmlLoader.load());
    }
}
