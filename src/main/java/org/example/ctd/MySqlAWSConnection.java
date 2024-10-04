package org.example.ctd;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlAWSConnection {

    Dotenv dotenv = Dotenv.configure().load();

    private final String urlForDB = dotenv.get("DB_URL_AWS"),
            usernameForDB = dotenv.get("DB_MASTER_USER"),
            passwordForDB = dotenv.get("DB_MASTER_PASSWORD");

    private final String url = urlForDB,
            user = usernameForDB,
            password = passwordForDB;

    Connection connection = null;

    public void connectToTheDatabase(){
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

        }catch (SQLException e){
            System.out.println("Connection Failed " + e);
            System.exit(1);
        }finally {
            if (connection != null){
                try {
                    System.out.println("Connection are now close to AWS");
                    connection.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
