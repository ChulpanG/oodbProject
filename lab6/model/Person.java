package lab6.model;

import lab6.annotation.Column;
import lab6.annotation.Entity;

@Entity
public class Person {
    @Column
    public int personID;
    @Column
    public String surname;
    @Column
    public String name;
    @Column
    public String position;
    @Column
    public String birthDate;

    private Integer personalCode;

    public Person(Integer personID,String surname,String name,String position){
        this.personID = personID;
        this.surname = surname;
        this.name = name;
        this.position = position;

    }
    public Person(){
    }

    public int getPersonID() {
        return personID;
    }
    public void setPersonID(int personID) {
        this.personID = personID;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position=position;
    }



}
