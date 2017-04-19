package sample.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Artur on 14.04.2017.
 */
public class Meal {
    SimpleStringProperty name;
    SimpleStringProperty ingredientsNumber;
    SimpleStringProperty totalCalories;

    ObservableList<Item> ingredients = FXCollections.observableArrayList();

    public Meal(String name, String ingredientsNumber,String totalCalories) {
        this.name =new SimpleStringProperty( name);
        this.ingredientsNumber = new SimpleStringProperty(ingredientsNumber);
        this.totalCalories =new SimpleStringProperty( totalCalories);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIngredientsNumber() {
        return ingredientsNumber.get();
    }

    public SimpleStringProperty ingredientsNumberProperty() {
        return ingredientsNumber;
    }

    public void setIngredientsNumber(String ingredientsNumber) {
        this.ingredientsNumber.set(ingredientsNumber);
    }

    public String getTotalCalories() {
        return totalCalories.get();
    }

    public SimpleStringProperty totalCaloriesProperty() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories.set(totalCalories);
    }
}
