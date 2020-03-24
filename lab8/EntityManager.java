package lab8;

import lab8.annotation.Column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityManager {
    
    public void persist(Object obj){

        int p = obj.getClass().getName().lastIndexOf('.');
        int l = obj.getClass().getName().length();
        String tableName = obj.getClass().getName().substring(p+1,l).toLowerCase();

        System.out.println(tableName);

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {

                    try {
                        Method method = obj.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);

                        System.out.println(method.getName());

                        String value = method.invoke(obj,null).toString();
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
}
