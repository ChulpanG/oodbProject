package lab7.main;

import lab7.annotation.*;
import lab7.model.Person;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    //connection props
    static class Auth {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String password = "2121";
    }

    public static String path = "lab7";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // проверка только полей без проверки с аннотациями многое ко многим
        ArrayList<String> listTableFields = new ArrayList<>();



        List<String> listTable = searchTables();
        for(int i = 0; i < listTable.size(); i++){
            listTableFields = (ArrayList<String>) searchFieldsInTable(listTable.get(i));
            String str =  listTable.get(i).substring(0,1).toUpperCase() + listTable.get(i).substring(1);


            System.out.println("Класс: " + str);
            compareFields(listTableFields, getAttributes(str));
        }



    }

    //Search all class
    public static List<Class<?>> searchAll(String path){
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(path);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("Bad package " + path);
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(searchAll(file, path));
        }
        return classes;
    }

    //вывод списка существующих таблиц
    public static List<String> searchTables() throws SQLException {
        List<String> listOfAttName = new ArrayList<>();
        Main.Auth auth = new Main.Auth();
        Connection connection = DriverManager.getConnection(auth.url, auth.login, auth.password);
        String sql = "SELECT table_name FROM information_schema.tables WHERE" +
                " table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema')";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            String attname = resultSet.getString("table_name");
            listOfAttName.add(attname);
        }
        ps.close();
        resultSet.close();
        return listOfAttName;
    }

    //вывод имен столбцов бд
    public static List<String> searchFieldsInTable(String tableName) throws SQLException {
        List<String> listOfAttName = new ArrayList<>();
        Main.Auth auth = new Main.Auth();
        Connection connection = DriverManager.getConnection(auth.url, auth.login, auth.password);
        String sql = "SELECT a.attname\n" +
                "FROM pg_catalog.pg_attribute a\n" +
                "WHERE a.attrelid = (\n" +
                "\tSELECT c.oid FROM pg_catalog.pg_class c \n" +
                "\tLEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace\n" +
                "\tWHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = '" +
                tableName + "')\n AND a.attnum > 0 AND NOT a.attisdropped";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            String attname = resultSet.getString("attname");
            listOfAttName.add(attname);
        }
        ps.close();
        resultSet.close();
        return listOfAttName;
    }

    // сравнение полей в бд и классе
    public static void compareFields(List<String> listDb, List<String> listPackage) {
        Collections.sort(listDb);
        Collections.sort(listPackage);
            for (int i = 0; i < listDb.size(); i++) {
                if (listDb.get(i).equals(listPackage.get(i))) {
                    System.out.println("Поля " + listDb.get(i) + " совпадают");
                } else {
                    System.out.println("Поля " + listDb.get(i) + " and " + listPackage.get(i) + " различны");
                }
            }
        }


    public static void compareTables(List<String> listTable, List<String> listClass) {
        Collections.sort(listTable);
        Collections.sort(listClass);
        if (listTable.size() != listClass.size()) {
            System.out.println("В базе не хватает таблиц");
        } else {
            for (int i = 0; i < listTable.size(); i++) {
                if (listTable.get(i).equals(listClass.get(i).toLowerCase())) {
                    System.out.println("Таблица " + listClass.get(i).toLowerCase() + " существует");
                } else {
                    System.out.println("Таблицы под именем" + listClass.get(i).toLowerCase() + "не существует");
                    break;
                }
            }
        }
    }

    //search all class
    public static List<Class<?>> searchAll(File file, String path){
        List<Class<?>> classes = new ArrayList<>();
        String resource = path + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(searchAll(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }


    // Получение всех классов с аннотацией "@Entity"
    public static boolean searchEntity(String path, String clsName){
        List<Class<?>> list = searchAll(path);
        List<Class<?>> resultList = new ArrayList<>();
        boolean result = false;
        if (list != null) {
            for (Class cls : list) {
                Annotation[] annotations = cls.getAnnotations();
                if (annotations != null) {
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType().equals(Entity.class)){
                            if((cls.getName().replace("lab7.model.", "")).equals(clsName)){
                                result = true;
                            }
                        }
                    }
                }
            }
        }
    return result;
    }

    // Получение имен филдов из класса
    public static List<String> getAttributes(String str) throws ClassNotFoundException {
        str = "lab7.model." + str;
        Class<?> cls = Class.forName(str);
        List<String> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation: annotations){
                    if (annotation.annotationType().equals(Column.class)||annotation.annotationType().equals(Id.class)){
                        fieldList.add(field.getName());
                    }

                    if (annotation.annotationType().equals(OneToMany.class)||annotation.annotationType().equals(ManyToOne.class)){
                        fieldList.add(field.getName() + "_id");
                    }
                }
            }
        }
        return fieldList;
    }

//    public static List<String> getAttribForClasses()

}
