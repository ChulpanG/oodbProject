package lab8.model;

import lab7.annotation.Column;
import lab7.annotation.Entity;
import lab7.annotation.Id;
import lab7.annotation.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Dish {

    @Id
    private int id;
    @Column
    private String name;
    @Column
    private double price;
    @ManyToMany
    @Column
    private Set<Orders> orders = new HashSet<>();

    Dish(String name, double price){

        this.name = name;
        this.price = price;

    }

    public int getDishID() {
        return id;
    }

    public void setDishID(int dishID) {
        this.id = id;
    }

    public String getDishName() {
        return name;
    }

    public void setDishName(String dishName) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Orders> getOrder() {
        return orders;
    }

    public void setOrder(Set<Orders> order) {
        this.orders = orders;
    }

}