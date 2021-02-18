package InventoryManager;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DBConnector {

    public static Connection getConnection() throws SQLException {

        //db.properties file not pushed in .gitignore
        String dbUrl = "";
        String dbName = "";
        String username = "";
        String password = "";

        try{
            //getClass().getResourceAsStream() can't be used in static context
            InputStream inputStream = DBConnector.class.getClassLoader().getResourceAsStream("local.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            dbUrl = properties.getProperty("dbUrl");
            dbName = properties.getProperty("dbName");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

        }catch (Exception e){
            e.printStackTrace();
        }

        Connection connection = DriverManager.getConnection(dbUrl + dbName, username, password);

        return connection;
    }

}
