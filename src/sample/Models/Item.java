package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Artur on 13.04.2017.
 */
public class Item {
    SimpleStringProperty nazwaProperty;
    SimpleStringProperty iloscProperty;
    SimpleStringProperty sumaProperty;

    public Item(String nazwaProperty, String iloscProperty, String sumaProperty) {
        this.nazwaProperty = new SimpleStringProperty(nazwaProperty);
        this.iloscProperty = new SimpleStringProperty(iloscProperty) ;
        this.sumaProperty = new SimpleStringProperty(sumaProperty);
    }

    public String getNazwaProperty() {
        return nazwaProperty.get();
    }

    public StringProperty nazwaPropertyProperty() {
        return nazwaProperty;
    }

    public void setNazwaProperty(String nazwaProperty) {
        this.nazwaProperty.set(nazwaProperty);
    }

    public String getIloscProperty() {
        return iloscProperty.get();
    }

    public StringProperty iloscProperty() {
        return iloscProperty;
    }

    public void setIloscProperty(String iloscProperty) {
        this.iloscProperty.set(iloscProperty);
    }

    public String getSumaProperty() {
        return sumaProperty.get();
    }

    public StringProperty sumaProperty() {
        return sumaProperty;
    }

    public void setSumaProperty(String sumaProperty) {
        this.sumaProperty.set(sumaProperty);
    }
}
