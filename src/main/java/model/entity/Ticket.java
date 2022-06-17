package model.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ticket {

    private SimpleIntegerProperty idTicket = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty idShow = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty idSeat = new SimpleIntegerProperty(0);
    private SimpleStringProperty creditCard = new SimpleStringProperty("");
    private SimpleStringProperty fullName = new SimpleStringProperty("");
    private SimpleIntegerProperty idSalle = new SimpleIntegerProperty(0);

    public Ticket(int idTicket, int idShow, String creditCard, String fullName, int idSalle, int idSeat) {
        setIdTicket(idTicket);
        setIdShow(idShow);
        setIdSeat(idSeat);
        setCreditCard(creditCard);
        setFullName(fullName);
        setIdSalle(idSalle);
    }

    public int getIdTicket() {
        return idTicket.get();
    }

    public SimpleIntegerProperty idTicketProperty() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket.set(idTicket);
    }

    public int getIdShow() {
        return idShow.get();
    }

    public SimpleIntegerProperty idShowProperty() {
        return idShow;
    }

    public void setIdShow(int idShow) {
        this.idShow.set(idShow);
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

    public String getCreditCard() {
        return creditCard.get();
    }

    public SimpleStringProperty creditCardProperty() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard.set(creditCard);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
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
}
