package lab9.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dish {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private double price;

    @ManyToMany(mappedBy = "dishes")
    private List<Orders> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Long getDishId() {
        return id;
    }

    public void setDishId(Long id) {
        this.id = id;
    }


//    public Set<Orders> getOrder() {
//        return orders;
//    }

//    public void setOrder(Set<Orders> order) {
//        this.orders = orders;
//    }

}