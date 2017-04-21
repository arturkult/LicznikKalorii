package sample.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Models.Meal;
import sample.Services.MealService;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    @FXML
    TableView<Meal> todayTableView;
    @FXML
    TableColumn<Meal,String> nazwaColumn;
    @FXML
    TableColumn<Meal,String> iloscColumn;
    @FXML
    TableColumn<Meal,String> sumaColumn;
    @FXML
    TableColumn<Meal,String> dateColumn;

    ObservableList<Meal> meals = FXCollections.observableArrayList();

    MealService mealService = new MealService();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        nazwaColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getName()));
        iloscColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getIngredientsNumber()));
        sumaColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getTotalCalories()));
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        dateColumn.setCellValueFactory(item->new SimpleObjectProperty<String>(dateFormat.format(item.getValue().getDate())));

        todayTableView.setItems(FXCollections.observableArrayList(mealService.listAllMeals()));
    }

    public void showItemViewer(Meal meal)
    {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/itemChooser.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodaj Posiłek");
            stage.setScene(new Scene(root1));
            ItemChooserController controller = fxmlLoader.getController();
            controller.initData(meal);
            stage.showAndWait();
            todayTableView.setItems(FXCollections.observableArrayList(mealService.listAllMeals()));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void addButtonClicked(ActionEvent actionEvent) {
        showItemViewer(null);
    }

    public void changeButtonClicked(ActionEvent actionEvent) {
        if (!todayTableView.getSelectionModel().isEmpty())
        {
            showItemViewer(todayTableView.getSelectionModel().getSelectedItem());
        }
    }
}
