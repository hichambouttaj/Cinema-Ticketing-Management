package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import model.entity.*;
import model.impl.ActorDAOImp;
import model.impl.MovieDAOImp;
import model.impl.SalleDAOImp;
import model.impl.ShowDAOImp;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionMovieViewController implements Initializable {

    private ShowDAOImp showDAOImp;
    private MovieDAOImp movieDAOImp;
    private SalleDAOImp salleDAOImp;
    private ActorDAOImp actorDAOImp;
    @FXML
    private FontAwesomeIconView searchBtn;
    @FXML
    private TableView<Show> tableViewShow;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Movie> tableViewMovie;
    @FXML
    private TextField searchBarMovie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieDAOImp = new MovieDAOImp();
        salleDAOImp = new SalleDAOImp();
        showDAOImp = new ShowDAOImp();
        actorDAOImp = new ActorDAOImp();
        tableViewMovie.setItems(movieDAOImp.getAll());
        tableViewMovie.setOnMouseClicked(mouseEvent -> {
            Movie movie = tableViewMovie.getSelectionModel().getSelectedItem();
            if(movie == null){
                return;
            }
            tableViewShow.setItems(showDAOImp.getAll(movie.getMovieId()));
        });
    }

    @FXML
    public void addMovieHandler(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Add New Movie");
        dialog.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/movieDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            MovieController movieController = fxmlLoader.getController();
            Movie movie = movieController.getNewMovie();
            if(!checkInput(movie)){
                return;
            }
            movieDAOImp.add(movie);
            tableViewMovie.setItems(movieDAOImp.getAll());
        }
    }

    @FXML
    public void updateMovieHandler(){
        Movie selectedMovie = tableViewMovie.getSelectionModel().getSelectedItem();
        if(selectedMovie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Movie Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the Movie you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Edit Movie");
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.initStyle(StageStyle.TRANSPARENT);
        fxmlLoader.setLocation(getClass().getResource("/view/movieDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        MovieController movieController = fxmlLoader.getController();
        movieController.editMovie(selectedMovie);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(!checkInput(movieController.getNewMovie())){
                return;
            }
            movieController.updateMovie(selectedMovie);
            movieDAOImp.update(selectedMovie);
        }
    }

    @FXML
    public void removeMovieHandler(){
        Movie selectedMovie = tableViewMovie.getSelectionModel().getSelectedItem();
        if(selectedMovie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Movie Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the movie you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete movie");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected Subs : " + selectedMovie.getTitle());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            movieDAOImp.delete(selectedMovie.getMovieId());
            tableViewMovie.setItems(movieDAOImp.getAll());
        }
    }

    //search movie by title
    @FXML
    public void searchMovieHandler(MouseEvent mouseEvent){
        String title = searchBarMovie.getText();
        tableViewMovie.setItems(movieDAOImp.find(title));
    }

    @FXML
    public void addShowHandler(ActionEvent actionEvent) {
        Movie selectedMovie = tableViewMovie.getSelectionModel().getSelectedItem();
        if(selectedMovie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Movie Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the Movie you want to add Show.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Add Show");
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.initStyle(StageStyle.TRANSPARENT);
        fxmlLoader.setLocation(getClass().getResource("/view/showDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            ShowController showController = fxmlLoader.getController();
            Show show = showController.getNewShow(selectedMovie);
            if(!checkInput(show)){
                return;
            }
            showDAOImp.add(show);
            tableViewShow.setItems(showDAOImp.getAll(selectedMovie.getMovieId()));
        }
    }

    public void removeShowHandler(ActionEvent actionEvent) {
        Movie selectedMovie = tableViewMovie.getSelectionModel().getSelectedItem();
        Show selectedShow = tableViewShow.getSelectionModel().getSelectedItem();
        if(selectedShow == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Show Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the show you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete show");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected show : " + selectedShow.getShowDate() + " salle : " + salleDAOImp.getOne(selectedShow.getSalleId()));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            showDAOImp.delete(selectedShow.getShowId());
            tableViewShow.setItems(showDAOImp.getAll(selectedMovie.getMovieId()));
        }
    }

    public void updateShowHandler(ActionEvent actionEvent) {
        Show selectedShow = tableViewShow.getSelectionModel().getSelectedItem();
        if(selectedShow == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Show Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the Show you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Edit Show");
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.initStyle(StageStyle.TRANSPARENT);
        fxmlLoader.setLocation(getClass().getResource("/view/showDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        ShowController showController = fxmlLoader.getController();
        showController.editShow(selectedShow);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(!checkInput(showController.getNewShow(movieDAOImp.getOne(selectedShow.getMovieId())))){
                return;
            }
            showController.updateShow(selectedShow);
            showDAOImp.update(selectedShow);
        }
    }

    @FXML
    public void addActorHandler(){
        Movie selectedMovie = tableViewMovie.getSelectionModel().getSelectedItem();
        if(selectedMovie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Movie Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the Movie you want to add Actor.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        dialog.setTitle("Add Show");
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.initStyle(StageStyle.TRANSPARENT);
        fxmlLoader.setLocation(getClass().getResource("/view/actorDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            ActorController actorController = fxmlLoader.getController();
            Actor actor = actorController.getNewActor(selectedMovie);
            if(!checkInput(actor)){
                return;
            }
            actorDAOImp.addActorToMovie(actor.getActorId(), selectedMovie.getMovieId(), actor.getRole());
        }
    }

    //check input validation for movie
    private boolean checkInput(Movie movie){
        if(movie.getTitle() == null || movie.getDescription() == null || movie.getImage() == null || movie.getDirector() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all information");
            alert.showAndWait();
            return false;
        }

        if(movie.getTitle().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter title");
            alert.showAndWait();
            return false;
        }

        if(movie.getDirector().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter director");
            alert.showAndWait();
            return false;
        }

        if(movie.getDescription().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter description");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //check input validation for show
    private boolean checkInput(Show show){
        if(show.getShowDate() == null || show.getShowStart() == null || show.getShowEnd() == null || show.getPrice() == 0 || show.getSalleId() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all information");
            alert.showAndWait();
            return false;
        }

        if(show.getShowDate().toString().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter date show");
            alert.showAndWait();
            return false;
        }

        if(show.getShowStart().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter show start time");
            alert.showAndWait();
            return false;
        }

        if(Integer.parseInt(show.getShowStart()) < 9 || Integer.parseInt(show.getShowStart()) > 21){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid start time from 9 to 21");
            alert.showAndWait();
            return false;
        }

        if(show.getShowEnd().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter show end time");
            alert.showAndWait();
            return false;
        }

        if(Integer.parseInt(show.getShowEnd()) < 10 || Integer.parseInt(show.getShowEnd()) > 23){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid end time from 10 to 23");
            alert.showAndWait();
            return false;
        }

        if(show.getPrice() == 0.0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter price");
            alert.showAndWait();
            return false;
        }

        if (show.getSalleId() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please chose salle");
            alert.showAndWait();
            return false;
        }

        ObservableList<Show> list = showDAOImp.getShows(show.getShowDate(), show.getSalleId());
        for(Show s : list){
            if(s.getShowStart().equals(show.getShowStart()) && s.getShowEnd().equals(show.getShowEnd())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid input");
                alert.setHeaderText(null);
                alert.setContentText("Show already taken");
                alert.showAndWait();
                return false;
            }
            if(!(Integer.parseInt(show.getShowStart()) > Integer.parseInt(s.getShowEnd()) || Integer.parseInt(show.getShowEnd()) < Integer.parseInt(s.getShowStart()))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid input");
                alert.setHeaderText(null);
                alert.setContentText("You cant set show in that time");
                alert.showAndWait();
                return false;
            }
        }

        return true;
    }

    //check input validation for actor
    private boolean checkInput(Actor actor){
        if(actor == null || actor.getRole() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all information");
            alert.showAndWait();
            return false;
        }

        if(actor.getRole().trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter role of actor");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
