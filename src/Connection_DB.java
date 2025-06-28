import java.sql.*;

public class Connection_DB {
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/rh","root","root");
    }


}