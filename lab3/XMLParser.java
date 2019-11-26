package lab3;

import lab1.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class XMLParser {

    public static List<Person> XMLParser(String file) throws XMLStreamException, FileNotFoundException{

        List<Person> personList = new ArrayList<>();
        Person person = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));

        while (reader.hasNext()) {
            // получаем событие (элемент) и разбираем его по атрибутам
            XMLEvent xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("user")) {
                    person = new Person();
                    // Получаем атрибут id для каждого элемента User
                    Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                    if (idAttr != null) {
                        person.setPersonID(Integer.parseInt(idAttr.getValue()));
                    }
                } else if (startElement.getName().getLocalPart().equals("surname")) {
                    xmlEvent = reader.nextEvent();
                    person.setSurname(xmlEvent.asCharacters().getData());
                } else if (startElement.getName().getLocalPart().equals("name")) {
                    xmlEvent = reader.nextEvent();
                    person.setName(xmlEvent.asCharacters().getData());
                } else if (startElement.getName().getLocalPart().equals("position")) {
                    xmlEvent = reader.nextEvent();
                    person.setPosition(xmlEvent.asCharacters().getData());
                }
            }

            if (xmlEvent.isEndElement()) {
                EndElement endElement = xmlEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("user")) {
                    personList.add(person);
                }
            }
        }
        return personList;
    }

    public static void XMLnew(String file) throws ParserConfigurationException, org.xml.sax.SAXException, IOException, TransformerConfigurationException, TransformerException{

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        Element root = document.getDocumentElement();

        Collection<Person> persons = new ArrayList<Person>();
        persons.add(new Person(3,"G","CH","MANAGER"));

        for (Person person : persons) {
            // person elements
            Element newPerson = document.createElement("user");

            newPerson.setAttribute("id", Integer.toString(person.getPersonID()));

            Element surname = document.createElement("surname");
            surname.appendChild(document.createTextNode(person.getSurname()));
            newPerson.appendChild(surname);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(person.getName()));
            newPerson.appendChild(name);

            Element position = document.createElement("position");
            position.appendChild(document.createTextNode(person.getPosition()));
            newPerson.appendChild(position);

            root.appendChild(newPerson);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

    }

    public static void XMLSortByName(String file) throws XMLStreamException, FileNotFoundException{

        List<Person> list=new ArrayList<>();
        list = XMLParser(file);

        List<String> listString = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            listString.add(list.get(i).name);
        }
        Collections.sort(listString);
        for (int t=0; t<listString.size();t++){
            for (int k=0;k<list.size();k++){
                if ( listString.get(t).equals(list.get(k).name)){

                    System.out.println("ID: "+ list.get(k).personID
                            + ", Name: "+ list.get(k).name
                            +", Surname: " + list.get(k).surname
                            + ", Position: "+ list.get(k).position);

                }
            }
        }
    }
    public static void XMLSearchObject(String name, String file) throws XMLStreamException, FileNotFoundException{

        List<Person> list=new ArrayList<>();
        list = XMLParser(file);

        for(int i=0;i<list.size();i++){
            if(list.get(i).name.equals(name)){

                System.out.println("ID: "+ list.get(i).personID
                        + ", Name: "+ list.get(i).name
                        +", Surname: " + list.get(i).surname
                        + ", Position: "+ list.get(i).position);

            }
        }
    }
}