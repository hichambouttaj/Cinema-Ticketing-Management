package model.entity;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;
import java.time.LocalDate;

public class Customer implements Serializable {

    private SimpleIntegerProperty customerId = new SimpleIntegerProperty(0);
    private LocalDate subsEnd;
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");

    public Customer(int customerId, LocalDate subsEnd, String firstName, String lastName, String phone, String address) {
        setCustomerId(customerId);
        this.subsEnd = subsEnd;
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public LocalDate getSubsEnd() {
        return subsEnd;
    }

    public void setSubsEnd(LocalDate subsEnd) {
        this.subsEnd = subsEnd;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", subsEnd=" + subsEnd +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", phone=" + phone +
                ", address=" + address +
                '}';
    }
}
