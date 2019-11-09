package lab2;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lab1.Person;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;

import lab3.XMLParser;
import org.xml.sax.SAXException;
public class JsonParser{


    //parse json file
    public static List<Person> JsonParser(String file) throws FileNotFoundException{

        Gson gson=new Gson();
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

        JsonObject a = (JsonObject) parser.parse(new FileReader(file));
        JsonObject person = (JsonObject) a;

        JsonArray array=person.getAsJsonArray("person");
        int size=array.size();
        int personID;
        String name = "";
        String surname = "";
        String position = "";
        JsonObject arrayObject = null;
        List<Person> listOfName = new ArrayList();
        System.out.println(size);

        for (int i = 0; i < array.size(); i++){

            arrayObject = array.get(i).getAsJsonObject();
            personID = arrayObject.get("personID").getAsInt();
            name = arrayObject.get("name").getAsString();
            surname = arrayObject.get("surname").getAsString();
            position = arrayObject.get("position").getAsString();
            Person personTest = new Person(personID,surname,name,position);
            listOfName.add(i, personTest);

        }
        return listOfName;
    }
    //поиск по имени
    public static void JsonSearchName(String name, String file) throws FileNotFoundException{

        List<Person> list = new ArrayList();
        list = JsonParser(file);

        for (int i=0; i<list.size();i++){
            if (list.get(i).name.equals(name)){
                System.out.println("ID: "+ list.get(i).personID
                        + ", Name: "+ list.get(i).name
                        + ", Surname: " + list.get(i).surname
                        + ", Position: "+ list.get(i).position);
            }
        }
    }
    //new data
    public static void JsonEdit(String file) throws FileNotFoundException, IOException{

        List<Person> list = new ArrayList();
        list = JsonParser(file);

        Gson gson=new Gson();
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

        JsonObject a = (JsonObject) parser.parse(new FileReader(file));
        JsonObject person = (JsonObject) a;
        JsonArray array=person.getAsJsonArray("person");

        JsonObject jobject = new JsonObject();
        jobject.addProperty("name", "name");
        jobject.addProperty("surname", "surname");
        jobject.addProperty("personID", 1);
        jobject.addProperty("position", "position");

        array.add(jobject);

        try (FileWriter fileJson = new FileWriter(file)) {
            fileJson.write("{\n" + "\"person\":\n" + array.toString()+"}");
            fileJson.flush();
        }
    }
    //sort for name
    public static void JsonSortName(String file) throws FileNotFoundException{

        List<Person> list = new ArrayList();
        list = JsonParser(file);

        List<String> sort = new ArrayList();
        for (int j=0; j<list.size(); j++){
            sort.add(list.get(j).name);
        }
        Collections.sort(sort);

        for (int t=0; t<sort.size();t++){
            for (int k=0;k<list.size();k++){
                if ( sort.get(t).equals(list.get(k).name)){

                    System.out.println("ID: "+ list.get(k).personID
                            + ", Name: "+ list.get(k).name
                            +", Surname: " + list.get(k).surname
                            + ", Position: "+ list.get(k).position);

                }
            }
        }
    }
}