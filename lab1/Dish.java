
package lab1;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Dish {

    int dishID;
    String dishName;
    double price;
    List<ProductDish> productDish;
    ArrayList<Dish> order= new ArrayList();

    public Dish(int dishID,String dishName,double price){
        this.dishID = dishID;
        this.dishName = dishName;
        this.price = price;

    }
    public int getDishId(){
        return dishID;
    }
    public String getDishName(){
        return dishName;
    }
    public double getPrice(){
        return price;
    }
    public void getDishStructure(int dishID){

    }
    public void addDish(Dish dish){
        order.add(dish);
    }
    public void removeDish(Dish dish){
        order.remove(dish);

    }
}
