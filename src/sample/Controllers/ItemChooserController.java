package sample.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import sample.Models.Item;
import sample.Models.Meal;
import sample.Services.MealService;

import javax.xml.soap.Text;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by Artur on 13.04.2017.
 */
public class ItemChooserController implements Initializable
{
    @FXML
    TableView<Item> itemsViewTable;
    @FXML
    TableColumn<Item,String> itemNameColumn;
    @FXML
    TableColumn<Item,String> itemAmountColumn;
    @FXML
    TableView<Item> selectedItemTableView;
    @FXML
    TableColumn<Item,String> selectedItemNameColumn;
    @FXML
    TableColumn<Item,String> selectedItemAmountColumn;
    @FXML
    TextField searchTextField;
    @FXML
    TextField mealNameField;
    @FXML
    Label statusLabel;
    @FXML
    DatePicker datePicker;
    @FXML
    TableColumn<Meal,String> selectedItemSumColumn;

    ObservableList<Item> items = FXCollections.observableArrayList();
    ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    MealService mealService = new MealService();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemNameColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getNazwaProperty()));
        itemAmountColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getIloscProperty()));
        selectedItemNameColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getNazwaProperty()));
        selectedItemAmountColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getIloscProperty()));

        datePicker.setValue(LocalDate.now());

        items = FXCollections.observableArrayList(mealService.listAllItems());
        itemsViewTable.setItems(items);
        //itemsViewTable.setItems(FXCollections.observableList(mealService.listAllItems()));

    }
    public void initData(Meal meal)
    {
        if(meal !=null)
        {
            selectedItems = FXCollections.observableArrayList(mealService.getItems(meal.getName()));
            selectedItemTableView.setItems(selectedItems);
            //selectedItemTableView.setItems(FXCollections.observableList(mealService.getItems(meal.getName())));
            mealNameField.setText(meal.getName());

            DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
            LocalDate localDate = LocalDate.parse(dateFormat.format(meal.getDate()), formatter);
            datePicker.setValue(localDate);
        }
    }
    public void addSelectedItemButtonClicked(ActionEvent actionEvent) {
        if(!itemsViewTable.getSelectionModel().isEmpty())
        {
            selectedItems.add(itemsViewTable.getSelectionModel().getSelectedItem());
        }
        selectedItemTableView.setItems(selectedItems);
    }

    public void removeSelectedItemButtonClicked(ActionEvent actionEvent) {
        if(!selectedItemTableView.getSelectionModel().isEmpty())
        {
            selectedItems.remove(selectedItemTableView.getSelectionModel().getSelectedItem());
        }
        selectedItemTableView.setItems(selectedItems);
    }

    public void saveButtonClicked(ActionEvent actionEvent) {
        if(mealNameField.getCharacters()!=null && selectedItems.size()>0)
        {
            LocalDate localDate = datePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            mealService.addMeal(mealNameField.getText(),selectedItems, date);
        }
        else statusLabel.setText("Nie podano wszystkich parametrów");
    }

    public void searchTextFieldOnKeyTyped(KeyEvent keyEvent) {
        ObservableList<Item> searchList = FXCollections.observableArrayList();
        if(searchTextField.getCharacters().length()!=0)
        {
            for (Item item:items) {
                if(item.getNazwaProperty().contains(searchTextField.getCharacters()))
                    searchList.add(item);
            }
            itemsViewTable.setItems(searchList);
        }
        else
        {
            itemsViewTable.setItems(items);
        }
    }

}
