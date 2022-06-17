package model.entity;

import javafx.beans.property.SimpleIntegerProperty;

public class Seat {
    private SimpleIntegerProperty idSeat = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty idSalle = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty seatNumber = new SimpleIntegerProperty(0);


    public Seat(int idSeat, int idSalle, int seatNumber) {
        setIdSeat(idSeat);
        setSeatNumber(seatNumber);
        setIdSalle(idSalle);
    }

    public int getIdSeat() {
        return idSeat.get();
    }

    public SimpleIntegerProperty idSeatProperty() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat.set(idSeat);
    }

    public int getIdSalle() {
        return idSalle.get();
    }

    public SimpleIntegerProperty idSalleProperty() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle.set(idSalle);
    }

    public int getSeatNumber() {
        return seatNumber.get();
    }

    public SimpleIntegerProperty seatNumberProperty() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber.set(seatNumber);
    }

    @Override
    public String toString() {
        return seatNumber.get() + "";
    }
}
