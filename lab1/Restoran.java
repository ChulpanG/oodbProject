package lab1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lab2.NewJsonParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Restoran {

    private List<Order> order = new ArrayList<>();
    private List<Dish> dish = new ArrayList<>();
    private List<Person> person = new ArrayList<>();

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Dish> getDish() {
        return dish;
    }

    public void setDish(List<Dish> dish) {
        this.dish = dish;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    public static void createJson() throws IOException, ClassNotFoundException {
        Dish dish = new Dish(1, "DishName", 11.00);
        NewJsonParser.newJsonObject("jsonDish", dish);
        List<Dish> listDish = new ArrayList<>();
        listDish.add(dish);
        Order order = new Order(1, 2, "2019-02-05 12:00", listDish);
        NewJsonParser.newJsonObject("jsonOrder", order);
        List<Order> listOrder = new ArrayList<>();
        listOrder.add(order);
        //Person person = (Person) NewJsonParser.parseJson("jsonPerson",Person.class);
        Person person = new Person(3, "Sur", "Name", "pos");
        List<Person> listPerson = new ArrayList<>();
        listPerson.add(person);


        FileWriter writer = new FileWriter("src/dataFiles/jsonRest.json");
        String jsonStrDish = new Gson().toJson(listDish);
        String jsonStrOrder = new Gson().toJson(listOrder);
        String jsonStrPerson = new Gson().toJson(listPerson);
        writer.write("{\"Dish\":" + jsonStrDish);
        writer.append("\n");
        writer.write("\"Order\":" + jsonStrOrder + ",");
        writer.append("\n");
        writer.write("\"Person\":" + jsonStrPerson + "}");
        writer.append("\n");
        writer.flush();
        writer.close();
    }
}
