package lab2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lab1.Person;
import lab1.Restoran;
import lab3.XMLParser;
import lab4.Database;
import lab4.Product;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewJsonParser {
    public static void main(String[] args) throws IOException, SAXException, XPathExpressionException, XMLStreamException, Exception {

        //String fileJson = "/Users/culpan/Desktop/PeopleJavaJson.json";
        //String fileXML = "/Users/culpan/Desktop/Users.xml";
        //Restoran.createJson();
        //Person[] person = (Person[]) parseJson("jsonPerson", "lab1.Person", "Person");

        //System.out.println(person);
        //Person prs = (Person) parseJson("jsonPerson", Person.class);
        //List<Person> list =  new ArrayList<>();
        //Person person = new Person(1,"Galimova","Chulpan","manager");
        //Person person1 = new Person(2,"G","CH","position");
        //list.add(person);
        //list.add(person1);
        //newJsonList("jsonPerson", list);
        //sortByCriteria("jsonPerson","lab1.Person");

        //JsonParser(fileJson,"person");
        //JsonSortName(fileJson);
        //JsonEdit(fileJson);
        //JsonSearchName("Алина",fileJson);

        //XMLParser.XMLParser(fileXML);
        //XMLParser.XMLnew(fileXML);
        //XMLParser.XMLSortByName(fileXML);
        //XMLParser.XMLSearchObject("Алия",fileXML);

        Product product = new Product(1,"pepper","p",1);

        int i = 0;
        Database database = new Database();
        i = database.saveToDB(product);
        database.loadFromDB();
        database.searchInDB();
        database.sortByASC();

    }

    /*public static Class[] parseJson(String fileName, String className, String chooseClassName) throws IOException, ClassNotFoundException {
        List result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/dataFiles/"+fileName+".json"));
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        //result = toList(reader, Class.forName(className));

        return wrapper;
    }*/

    public static <T> List<T> toList(String json,Class<T> clas){
        if(null == json){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }

    public static void newJsonObject(String fileName, Object object) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/dataFiles/"+fileName+".json",true));
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        writer.append(gson.toJson(object));
    }

    public static void newJsonList(String fileName, List listForParse) throws IOException, ClassNotFoundException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("src/dataFiles/"+fileName+".json"));
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        //String str =
        writer.close();
    }

    public static void sortByCriteria(String fileName, Class className) throws IOException, ClassNotFoundException, NoSuchMethodException {
       /* List list = parseJson(fileName, className);
        List listSt = new ArrayList<>();
        //Method method = list.getClass().getMethod("getPersonID","id");
        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i).getClass().getMethod("getPersonID", Class.forName("id")));
        }
        //Collections.sort(listSt);

        int k = 0;
        for (int j = 0; j < list.size(); j++) {
            if (listSt.get(j).equals(list.get(j).getClass().getDeclaredMethod("getPersonID"))) {
                System.out.println(list.get(k).toString());
                k = 0;
            } else {
                k++;
            }
        }
    }
    public static void searchByCriteria(String fileName, String className, String searchName,String chooseCrit) throws IOException, ClassNotFoundException, NoSuchMethodException {
        List list = parseJson(fileName, className);
        for(int i=0; i < list.size(); i++){
            if (searchName.equals(list.get(i).getClass().getDeclaredMethod("get"+chooseCrit))){
                System.out.println(list.get(i).toString());
            }
        }*/
    }
}
