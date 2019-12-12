package lab5;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.insertOrder(2, new PersonType("Nur", "A", "dev", "1999-03-22 00:00:00"), Timestamp.valueOf(LocalDateTime.now()));
        //db.getAllOrders();
        //db.getOrderById(1);
        db.getOrderByTime("2019-12-07 13:11:46.812");
    }
}
