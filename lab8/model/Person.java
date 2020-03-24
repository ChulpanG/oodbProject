package lab8.model;

import lab8.annotation.Column;
import lab8.annotation.Entity;

@Entity
public class Person {
    @Column
    public int id;
    @Column
    public String surname;
    @Column
    public String name;
    @Column
    public String position;
    @Column
    public String birthdate;
    @Column
    public String gender;
    @Column
    public int personalcode;

    public Person(Integer id, String surname, String name, String position, String birthdate,
                  String gender, Integer personalcode){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.position = position;
        this.birthdate = birthdate;
        this.gender = gender;
        this.personalcode = personalcode;

    }
    public Person(){
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPersonalcode() {
        return personalcode;
    }

    public void setPersonalcode(int personalcode) {
        this.personalcode = personalcode;
    }



}
