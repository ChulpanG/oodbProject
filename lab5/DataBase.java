package lab5;

import java.sql.*;

public class DataBase {

    Auth auth = new Auth();

    class Auth {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String password = "2121";
    }

    public void insertOrder(Integer orderID, PersonType person, Timestamp time) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(auth.url, auth.login, auth.password);
        String sql = String.format("INSERT INTO orders(orderID, person, time) values(%s, row('%s', '%s', '%s', '%s'), '%s');",
                orderID, person.getSurname(),person.getName(), person.getPosition(), person.getBirthDate(), time.toString());
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
    }

    public void getAllOrders() throws SQLException {
        Connection connection = DriverManager.getConnection(auth.url, auth.login, auth.password);
        String sql = "select * from orders";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            String order = resultSet.getString("id");
            System.out.println(order);
        }
    }

    public void getOrderById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(auth.url, auth.login, auth.password);
        String sql = String.format("select * from orders where id = %s", id);
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            String order = resultSet.getString("person");
            System.out.println(order);
        }
    }

    public void getOrderByTime(String time) throws SQLException{
        Connection connection = DriverManager.getConnection(auth.url, auth.login, auth.password);
        String sql = String.format("select * from orders where time = '%s'", time);

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            String order = resultSet.getString("id");
            System.out.println(order);
        }
    }

}
