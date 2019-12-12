package lab6;

import lab6.annotation.Column;
import lab6.annotation.Entity;
import lab6.model.Order;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String path = "lab6";

    public static void main(String[] args) {
        System.out.println("Step 1: Scanning path - " + path);

        List<Class<?>> classList = searchAll(path);
        if (classList != null){
            classList.forEach(c -> System.out.println("\t* " + c.getCanonicalName()));
        }

        System.out.println("\nStep 2: Scanning class fields");
        for(Class<?> cls: classList){
            System.out.println("\t* " + cls.getName());
            Field[] fields = cls.getDeclaredFields();
            for (Field field: fields){
                System.out.println("\t\t- " + field.getName());
            }
        }

        System.out.println("Step 3: scanning class methods - " + path);
        for (Class<?> cls: classList){
            System.out.println("\tFields of class " + cls.getName());
            Method[] methods = cls.getMethods();
            for (Method method: methods){
                System.out.println("\t\t - " + method.getName());
            }
        }

        System.out.println("Step 4: scanning class annotations - " + path);
        Annotation[] annotations = Order.class.getAnnotations();
        if (annotations != null){
            for (Annotation a: annotations){
                if (a.annotationType().equals(Entity.class)){
                    System.out.println("\tOrder is entity");
                }
            }
        }

        System.out.println("Step 5: scanning fields annotations - " + path);
        Field[] fields = Order.class.getDeclaredFields();
        for (Field f : fields){
            Annotation[] fannotations = f.getAnnotations();
            for (Annotation a: fannotations){
                if (a.annotationType().equals(Column.class)){
                    System.out.println(String.format("\t Field %s %s is attribute!", f.getType().getName(), f.getName()));
                }
            }
        }

        System.out.println("Step 6: get superclass - " + path);
        Class superClass = Order.class.getSuperclass();
        if (superClass != null){
            System.out.println("\tSuper class of Order is " + superClass.getName());
        } else {
            System.out.println("\tOrder class has no super class");
        }
    }

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
    public static List<Class<?>> getClasses(String path){
        List<Class<?>> list = searchAll(path);
        List<Class<?>> resultList = new ArrayList<>();
        if (list != null) {
            for (Class cls : list) {
                Annotation[] annotations = cls.getAnnotations();
                if (annotations != null) {
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType().equals(Entity.class)){
                            resultList.add(cls);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    public static List<Field> getAttributes(Class<?> cls){
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation: annotations){
                    if (annotation.annotationType().equals(Column.class)){
                        fieldList.add(field);
                    }
                }
            }
        }
        return fieldList;
    }
}
