package sample.Services;

import sample.Models.Item;
import sample.Models.Meal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Artur on 20.04.2017.
 */
public class MealService {

    public List<Meal> listAllMeals(){
        List<Meal> meals = new ArrayList<>();
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
        return meals;
    }

    public List<Item> listAllItems(){
        List<Item> items = new ArrayList<>();
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
        return items;
    }


    public void addMeal(String mealName,List<Item> items){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/sample/Meals/mealInfo.txt",true));
            BufferedWriter meal = new BufferedWriter(new FileWriter("src/sample/Meals/"+mealName+".txt"));
        )
        {
            bw.write(mealName);
            bw.write("\t");
            bw.write(Integer.toString(items.size()));
            bw.write("\t");
            bw.write(Integer.toString(sumItems(items)));
            bw.newLine();
            for(Item item:items)
            {
                meal.write(item.getNazwaProperty());
                meal.write("\t");
                meal.write(item.getIloscProperty());
                meal.write("\t");
                meal.write(item.getSumaProperty());
                meal.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getItems(String mealName){
        List<Item> items = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/Meals/"+mealName+".txt")))
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
        return items;
    }

    public int sumItems(List<Item> items)
    {
        int sum = 0;
        for (Item item:items
                ) {
            sum+=Integer.parseInt(item.getIloscProperty())*Integer.parseInt(item.getSumaProperty());
        }
        return sum;
    }
}
