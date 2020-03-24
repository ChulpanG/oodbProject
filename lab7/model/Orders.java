package lab7.model;


import lab7.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Orders {
    @Id
    private int id;

    @ManyToOne
    private Person person;

    @Column
    private String time;

    @ManyToMany
    private Set<Dish> dish = new HashSet<>();

    Orders(int id, Person person, String time){
        this.id = id;
        this.person = person;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
