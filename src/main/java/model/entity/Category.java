package model.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Category {
    private SimpleIntegerProperty categoryId = new SimpleIntegerProperty(0);
    private SimpleStringProperty genre = new SimpleStringProperty("");

    public Category(int categoryId, String genre) {
        setCategoryId(categoryId);
        setGenre(genre);
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public SimpleIntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    @Override
    public String toString() {
        return genre.get();
    }
}
