package lab8;

import lab8.annotation.Column;
import lab8.model.Person;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class EntityManagerExample {

    private Connection connection;

    private Properties dbProperties;

    public EntityManagerExample(Properties dbProperties) {
        this.dbProperties = dbProperties;
        getConnection();
    }

    public static void main(String[] args) {

        Properties dbProperties = new Properties();
        dbProperties.setProperty("url","jdbc:postgresql://localhost:5432/postgres");
        dbProperties.setProperty("username","postgres");
        dbProperties.setProperty("password","2121");

        EntityManagerExample epe = new EntityManagerExample(dbProperties);

        Person person = new Person();
        person.setName("ExampleName");
        person.setBirthdate("22.03.99");
        person.setId(1234);
        person.setPersonalcode(1111);
        person.setSurname("SurnameExample");
        person.setGender("m");
        person.setPosition("dev");

        epe.persist(person);

        epe.close();
    }

    public void persist(Object var1) {

        int p = var1.getClass().getName().lastIndexOf('.');
        int l = var1.getClass().getName().length();
        String tableName = var1.getClass().getName().substring(p+1,l).toLowerCase();

        System.out.println(tableName);

        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {

                    try {
                        Method method = var1.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);

                        System.out.println(method.getName());

                        String value = method.invoke(var1,null).toString();
                        System.out.println(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println(field.getName());
                }
            }
        }

    }

    public Connection getConnection() {

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                connection = DriverManager.getConnection(dbProperties.getProperty("url"),
                        dbProperties.getProperty("username"), dbProperties.getProperty("password"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public void close() {
        closeConnection();
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
