
package lab1;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private int waiterID;
    private String time;
    List<Dish> order=new ArrayList();
    Order(int orderID,int waiterID,String time,List<Dish> order){
        this.orderID = orderID;
        this.waiterID = waiterID;
        this.time = time;
        this.order = order;
    }

    public int getOrderID(){
        return orderID;
    }
    public String getOrderTime(){
        return time;
    }
    public int getWaiterID(){
        return waiterID;
    }
    public List<Dish> getOrder(){
        return order;
    }

    public String getDish(){
        String str="";
        for (int i=0;i<order.size();i++) {
            str = str + order.get(i).getDishName()+", ";
        }
        return str;
    }
    public double calculateOrderPrice(){
        double sum=0;
        for (Dish i:order){
            sum+=i.getPrice();
        }
        return sum;
    }
    public void addDishToOrder(Dish dish){
        order.add(dish);
        calculateOrderPrice();
    }
    public void removeDishFromOrder(Dish dish){
        order.remove(dish);
        calculateOrderPrice();
    }
}
