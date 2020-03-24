package lab8.model;

import lab6.annotation.Column;
import lab6.annotation.Entity;
import lab6.annotation.ManyToMany;
import lab6.annotation.ManyToOne;
import lab7.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Orders {
    @Id
    private int id;

    @ManyToOne(join = "id")
    @Column
    private Person person;

    @Column
    private String time;

    @ManyToMany
    @Column
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
