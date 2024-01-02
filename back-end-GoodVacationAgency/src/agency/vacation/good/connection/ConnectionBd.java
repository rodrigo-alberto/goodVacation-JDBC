package agency.vacation.good.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBd {
	public static Connection connection;

    public static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_GoodVacationAgency", "root", "");
            } catch (SQLException e) {
                throw new RuntimeException("\n\nError:\n"+e);
            }
        }
        
        return connection;
    }
}