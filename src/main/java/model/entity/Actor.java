package model.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Actor {
    private SimpleIntegerProperty actorId = new SimpleIntegerProperty(0);
    private SimpleStringProperty fullName = new SimpleStringProperty("");

    private SimpleStringProperty role = new SimpleStringProperty("");

    public Actor(int actorId, String fullName) {
        setActorId(actorId);
        setFullName(fullName);
    }

    public int getActorId() {
        return actorId.get();
    }

    public SimpleIntegerProperty actorIdProperty() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId.set(actorId);
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

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    @Override
    public String toString() {
        return fullName.get();
    }
}
