package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.entity.*;
import model.impl.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {

    private ShowDAOImp showDAOImp;
    private SalleDAOImp salleDAOImp;
    private ActorDAOImp actorDAOImp;
    private MovieDAOImp movieDAOImp;
    private CategoryDAOImp categoryDAOImp;
    @FXML
    private ComboBox<Category> categoryList;

    @FXML
    private Label salleName;
    @FXML
    private Label descriptionField;

    @FXML
    private Label directorField;

    @FXML
    private ImageView image;

    @FXML
    private Label movieTitle;

    @FXML
    private TextField searchBarMovie;

    @FXML
    private TableView<Actor> tableViewActors;

    @FXML
    private TableView<Show> tableViewShows;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        movieDAOImp = new MovieDAOImp();
        showDAOImp = new ShowDAOImp();
        actorDAOImp = new ActorDAOImp();
        categoryDAOImp = new CategoryDAOImp();
        salleDAOImp = new SalleDAOImp();

        tableViewShows.setOnMouseClicked(mouseEvent -> {
            Show show = tableViewShows.getSelectionModel().getSelectedItem();
            if(show != null){
                Salle salle = salleDAOImp.getOne(showDAOImp.getSalleId(show.getShowId()));
                salleName.setText(salle.getSalleName());
            }
        });
    }

    @FXML
    void getTicketHandler(ActionEvent actionEvent) throws IOException {
        if(tableViewShows.getSelectionModel().getSelectedItem() != null){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/addTicketView.fxml"));
            vBox.getChildren().removeAll();
            vBox.getChildren().setAll((Node) fxmlLoader.load());
            AddTicketViewController addTicketViewController = fxmlLoader.getController();
            addTicketViewController.setTicketDetails(movieDAOImp.getOne(tableViewShows.getSelectionModel().getSelectedItem().getMovieId()));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Show Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the show you want.");
            alert.showAndWait();
            return;
        }
    }

    //search movie by title
    @FXML
    void searchBtnHandler(MouseEvent mouseEvent) {
        if(searchBarMovie == null){
            init();
            return;
        }
        if(!searchBarMovie.getText().trim().isEmpty()){
            Movie movie = movieDAOImp.getOne(searchBarMovie.getText());
            if(movie != null){
                searchBarMovie.setText(movie.getTitle());
                setMovieDetails(movie);
            }else{
                init();
            }
        }else{
            init();
        }
    }

    public void setMovieDetails(Movie movie){
        movieTitle.setText(movie.getTitle());
        directorField.setText(movie.getDirector());
        descriptionField.setText(movie.getDescription());
        ObservableList<Actor> list= actorDAOImp.getAllByMovieId(movie.getMovieId());
        for(int i = 0 ; i < list.size(); i++){
            list.get(i).setRole(actorDAOImp.getRole(movie.getMovieId(), list.get(i).getActorId()));
        }
        tableViewActors.setItems(list);
        tableViewShows.setItems(showDAOImp.getAll(movie.getMovieId()));
        categoryList.setItems(categoryDAOImp.getAllByMovieId(movie.getMovieId()));
        if(categoryList.getItems().size() > 0){
            categoryList.setValue(categoryList.getItems().get(0));
        }
        image.setImage(new Image(movie.getImage()));
    }

    public void init(){
        movieTitle.setText(null);
        directorField.setText(null);
        descriptionField.setText(null);
        tableViewActors.setItems(null);
        tableViewShows.setItems(null);
        categoryList.setItems(null);
        categoryList.setValue(null);
        salleName.setText(null);
        image.setImage(null);
    }


}
