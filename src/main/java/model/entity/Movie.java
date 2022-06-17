package model.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private SimpleIntegerProperty movieId = new SimpleIntegerProperty(0);
    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty director = new SimpleStringProperty("");
    private SimpleStringProperty description = new SimpleStringProperty("");
    private String image;

    public Movie(int movieId, String title, String director, String description, String image) {
        setMovieId(movieId);
        setTitle(title);
        setDirector(director);
        setDescription(description);
        this.image = image;
    }

    public int getMovieId() {
        return movieId.get();
    }

    public SimpleIntegerProperty movieIdProperty() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDirector() {
        return director.get();
    }

    public SimpleStringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return title.get();
    }
}
