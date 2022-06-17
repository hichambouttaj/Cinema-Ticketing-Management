package model.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Show {
    private SimpleIntegerProperty showId = new SimpleIntegerProperty(0);
    private LocalDate showDate;
    private SimpleStringProperty showStart = new SimpleStringProperty("");
    private SimpleStringProperty showEnd = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);
    private SimpleIntegerProperty movieId = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty salleId = new SimpleIntegerProperty(0);

    public Show(int showId, LocalDate showDate, String showStart, String showEnd, double price, int movieId, int salleId) {
        setShowId(showId);
        setShowDate(showDate);
        setShowStart(showStart);
        setShowEnd(showEnd);
        setPrice(price);
        setMovieId(movieId);
        setSalleId(salleId);
    }

    public int getShowId() {
        return showId.get();
    }

    public SimpleIntegerProperty showIdProperty() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId.set(showId);
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public String getShowStart() {
        return showStart.get();
    }

    public SimpleStringProperty showStartProperty() {
        return showStart;
    }

    public void setShowStart(String showStart) {
        this.showStart.set(showStart);
    }

    public String getShowEnd() {
        return showEnd.get();
    }

    public SimpleStringProperty showEndProperty() {
        return showEnd;
    }

    public void setShowEnd(String showEnd) {
        this.showEnd.set(showEnd);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
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

    public int getSalleId() {
        return salleId.get();
    }

    public SimpleIntegerProperty salleIdProperty() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId.set(salleId);
    }

    @Override
    public String toString() {
        return showDate.toString();
    }
}
