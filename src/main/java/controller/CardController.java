package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.entity.Category;
import model.entity.Movie;
import model.impl.CategoryDAOImp;

import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    private CategoryDAOImp categoryDAOImp;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    @FXML
    private HBox box;

    @FXML
    private ImageView image;

    @FXML
    private Label movieGenre;

    @FXML
    private Label movieTitle;

    private String[] colors = {"1E90FC", "84ACFA", "7CD7F7", "A8EEFF", "8BCBFC"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        categoryDAOImp = new CategoryDAOImp();
    }

    public void setData(Movie movie){
        categories = categoryDAOImp.getAllByMovieId(movie.getMovieId());
        Image img = new Image(movie.getImage());
        image.setImage(img);
        movieTitle.setText(movie.getTitle());
        if(!categories.isEmpty()){
            movieGenre.setText(categories.get(0).getGenre());
        }
        box.setStyle("-fx-background-color: #" + colors[(int)(Math.random()*colors.length)] + ";" +
                " -fx-background-radius: 15;" +
                " -fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 10);");
    }

    public void init(){
        movieGenre.setText("");
        movieTitle.setText("");
        image.setImage(null);
    }

    @Override
    public String toString() {
        return "CardController{" +
                "image=" + image +
                ", movieGenre=" + movieGenre +
                ", movieTitle=" + movieTitle +
                '}';
    }
}
