package InventoryManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DBConnector {

    public static Connection getConnection() throws SQLException {

        String dbName = "";
        String username = "";
        String password = "";

        //database.properties file not committed in .gitignore
        try{
            InputStream inputStream = new FileInputStream("src/database.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            dbName = properties.getProperty("dbName");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

        }catch (Exception e){
            e.printStackTrace();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://65.19.141.67:3306/" + dbName, username, password);

        return connection;
    }

}
