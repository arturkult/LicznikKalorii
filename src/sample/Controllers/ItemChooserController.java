package sample.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sample.Models.Item;

import javax.xml.soap.Text;
import java.io.*;
import java.net.URL;
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


    ObservableList<Item> items = FXCollections.observableArrayList();
    ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemNameColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getNazwaProperty()));
        itemAmountColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getIloscProperty()));
        selectedItemNameColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getNazwaProperty()));
        selectedItemAmountColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getIloscProperty()));
        try
                (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/tabela.txt")))
        {
            String line;
            while((line=bufferedReader.readLine())!=null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "\t");
                String nazwa,ilosc;
                nazwa = stringTokenizer.nextToken();
                ilosc = stringTokenizer.nextToken();
                items.add(new Item(nazwa,ilosc,"1"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        itemsViewTable.setItems(items);
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
        if(mealNameField!=null && selectedItems.size()>0)
        {
            try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/sample/Meals/mealInfo.txt",true)))
            {
                bw.write(mealNameField.getText());
                bw.write("\t");
                bw.write(Integer.toString(selectedItems.size()));
                bw.write("\t");
                bw.write(Integer.toString(sumItems()));
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public int sumItems()
    {
        int sum = 0;
        for (Item item:selectedItems
             ) {
            sum+=Integer.parseInt(item.getIloscProperty())*Integer.parseInt(item.getSumaProperty());
        }
        return sum;
    }
}
