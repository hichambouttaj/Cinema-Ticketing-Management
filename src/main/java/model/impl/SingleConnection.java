package model.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnection {
    private String db = "mydb";
    private String user = "root";
    private String password = "toor";
    private String url = "jdbc:mysql://localhost:3306/" + db;

    private static Connection connection = null;

    private SingleConnection(){
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection .....");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        if(connection == null){
            new SingleConnection();
        }
        return connection;
    }

}
