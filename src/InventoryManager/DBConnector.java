package InventoryManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    public static Connection getConnection() throws SQLException {
        String dbName = "mingxc_InventoryManager";
        String userName = "";
        String password = "";

        try{
            InputStream inputStream = new FileInputStream("src/database.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            userName = properties.getProperty("userName");
            password = properties.getProperty("password");

        }catch (Exception e){
            e.printStackTrace();
        }

        //Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://65.19.141.67:3306/" + dbName, userName, password);

        return connection;
    }
}
