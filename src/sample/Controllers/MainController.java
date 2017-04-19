package sample.Controllers;

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
import javafx.stage.Stage;
import sample.Models.Item;
import sample.Models.Meal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

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

    ObservableList<Meal> meals = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        nazwaColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getName()));
        iloscColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getIngredientsNumber()));
        sumaColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getTotalCalories()));

        createItemList();

        todayTableView.setItems(meals);
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
            createItemList();
            todayTableView.setItems(meals);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void addButtonClicked(ActionEvent actionEvent) {
        showItemViewer(null);
    }
    public void createItemList(){
        try
                (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/Meals/mealInfo.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "\t");
                String nazwa, ilosc, suma;
                nazwa = stringTokenizer.nextToken();
                ilosc = stringTokenizer.nextToken();
                suma = stringTokenizer.nextToken();
                meals.add(new Meal(nazwa, ilosc, suma));
            }
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void changeButtonClicked(ActionEvent actionEvent) {
        if (!todayTableView.getSelectionModel().isEmpty())
        {

        }
    }
}
