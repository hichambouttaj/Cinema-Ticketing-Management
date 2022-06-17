package model.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Salle {
    private SimpleIntegerProperty salleId = new SimpleIntegerProperty(0);
    private SimpleStringProperty salleName = new SimpleStringProperty("");

    public Salle(int salleId, String salleName) {
        setSalleId(salleId);
        setSalleName(salleName);
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

    public String getSalleName() {
        return salleName.get();
    }

    public SimpleStringProperty salleNameProperty() {
        return salleName;
    }

    public void setSalleName(String salleName) {
        this.salleName.set(salleName);
    }

    @Override
    public String toString() {
        return salleName.get();
    }
}
