package lab7.model;

import lab7.annotation.Column;
import lab7.annotation.Entity;
import lab7.annotation.Id;

import java.sql.Timestamp;

@Entity
public class Person {
    @Id
    public int id;
    @Column
    public String surname;
    @Column
    public String name;
    @Column
    public String position;
    @Column
    public String birthDate;
    @Column
    public String gender;
    @Column
    public int personalcode;

    public Person(Integer id, String surname, String name, String position){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.position = position;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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
