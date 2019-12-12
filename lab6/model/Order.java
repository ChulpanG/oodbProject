package lab6.model;

import lab6.annotation.Column;
import lab6.annotation.Entity;
import lab6.annotation.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Column
    private int orderID;
    @ManyToOne(join = "personid")
    @Column
    private Person person;
    @Column
    private String time;


    Order(int orderID,Person person,String time){
        this.orderID = orderID;
        this.person = person;
        this.time = time;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
