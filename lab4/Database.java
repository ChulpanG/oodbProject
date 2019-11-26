package lab4;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    Gson gson = new Gson();

    public static Connection getDBConnection(){
        Connection dbConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String loginBD = "postgres";
            String passwordBD = "2121";
            dbConnection = DriverManager.getConnection(url, loginBD,passwordBD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public int saveToDB(Product product) {
        System.out.println("Сохранение в БД");
        String SQL_SELECT1 = "insert into datatest (json) values ('" + gson.toJson(product) + "') returning id";
        String SQL_SELECT2 = "insert into datatest (jsonb) values (cast(? as json)) returning id";
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT1)) {

            String object = gson.toJson(product);

            long start;
            long finish;

            //stmt.setString(1, object);

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time save JSON:  " + (finish - start));
           while (resultSet.next()) {
                 int i = resultSet.getInt("id");
                 return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT2)) {

            String object = gson.toJson(product);

            long start;
            long finish;

            stmt.setString(1, object);

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time save JSONB:  " + (finish - start));
            while (resultSet.next()) {
                int i = resultSet.getInt("id");
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void sortByASC() {
        List<String> json = new ArrayList<>();
        List<String> jsonb = new ArrayList<>();
        long start;
        long finish;
        System.out.println("Сортировка по ASC");
        String SQL_SELECT1 = "SELECT (json -> 'name') as json FROM datatest ORDER by jsonb ASC;";
        String SQL_SELECT2 = "SELECT (jsonb -> 'name') as jsonb FROM datatest ORDER by jsonb ASC;";
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT1)) {

            String example = "";
            Product product = null;

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time sort JSON:  " + (finish - start));

            while (resultSet.next()) {
                example = resultSet.getString("json");
                json.add(example);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT2)) {

            String example = "";
            Product product = null;

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time sort JSONB: " + (finish - start));

            while (resultSet.next()) {
                example = resultSet.getString("jsonb");
                jsonb.add(example);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\nJSON:");
        for (int i = 0; i < json.size(); i++) {
            System.out.println(json.get(i));
        }
        System.out.println("\nJSONB:");
        for (int i = 0; i < jsonb.size(); i++) {
            System.out.println(jsonb.get(i));
        }
    }

    public void searchInDB() {
        long start;
        long finish;
        System.out.println("Поиск в БД");
        String SQL_SELECT1 = "SELECT json -> 'name' as json FROM datatest";
        String SQL_SELECT2 = "SELECT jsonb -> 'name' as jsonb FROM datatest";
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT1)) {

            String example = "";
            Product product = null;

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time search JSON:  " + (finish - start));

            while (resultSet.next()) {
                example = resultSet.getString("json");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT2)) {

            String example = "";
            Product product = null;

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time search JSONB: " + (finish - start));

            while (resultSet.next()) {
                example = resultSet.getString("jsonb");
            }
            System.out.println("Поле 'name' содержит: " + example);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadFromDB() {

        long start;
        long finish;
        System.out.println("Считывание из БД");
        String SQL_SELECT1 = "select json from datatest";
        String SQL_SELECT2 = "select jsonb from datatest";
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT1)) {

            String example = "";
            Product product = null;

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time load JSON:  " + (finish - start));

            while (resultSet.next()) {
                example = resultSet.getString("json");
            }
            product = gson.fromJson(example, Product.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection con = getDBConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT2)) {

            String example = "";
            Product product = null;

            start = System.nanoTime();
            ResultSet resultSet = stmt.executeQuery();
            finish = System.nanoTime();
            System.out.println("time load JSONB: " + (finish - start));

            while (resultSet.next()) {
                example = resultSet.getString("jsonb");
            }
            product = gson.fromJson(example, Product.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

