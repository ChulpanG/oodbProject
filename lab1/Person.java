package lab1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Person {

    public int personID;
    public String surname;
    public String name;
    public String position;
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
    /*
    public String getBirthDate(){
        return birthDate;
    }
    public void setBirthDate(String birthDate){
        this.birthDate=birthDate;
    }
    public Gender getGender(){
        return gender;
    }
    public void setGender(Gender gender){
        this.gender=gender;
    }
    public void addPerson(Person person){
       if (!people.contains(person)) {
            people.add(person);
        }
    }*/
}
//enum Gender { MALE, FEMALE }

