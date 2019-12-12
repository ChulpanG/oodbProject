package lab5;

import org.postgresql.util.PGobject;
import org.postgresql.util.PGtokenizer;

import java.sql.Timestamp;

public class PersonType extends PGobject {

    public String surname;
    public String name;
    public String position;
    public String birthDate;

    public PersonType(){
        setType("person_type");
    }

    public void setValue(String value){
        String s = value.substring(1, value.length()-1);
        PGtokenizer t = new PGtokenizer(s, ',');
        surname = t.getToken(0);
        name = t.getToken(1);
        position = t.getToken(2);
        birthDate = t.getToken(3);
    }

    @Override
    public String getValue(){
        return super.getValue();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public PersonType(String surname, String name, String position, String birthDate){
       this.surname = surname;
       this.name = name;
       this.position = position;
       this.birthDate = birthDate;
    }
}
