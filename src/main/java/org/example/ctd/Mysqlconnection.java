package org.example.ctd;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.Main;
import org.example.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.example.Main.GetDataFromUser;

public class Mysqlconnection {

    Dotenv dotenv = Dotenv.configure().load();

    String urlForDB = dotenv.get("DB_URL");
    String usernameForDB = dotenv.get("DB_USER");

    String passwordForDB = dotenv.get("DB_PASSWORD");


    private final String url = urlForDB;
    private final String user = usernameForDB;
    private final String password = passwordForDB;

    private final String tableName = "works";

    String createTableSQL = "CREATE TABLE IF NOT EXISTS works ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "Company VARCHAR(100) NOT NULL,"
            + "Topic VARCHAR(100) NOT NULL,"
            + "Date VARCHAR(100) NOT NULL"
            + ")";

    public void connectTodatabase() {

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            System.out.println("Connected to the database!");
            if (!tableExeists(connection, tableName)) {
                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully!");
            } else {
                System.out.println("Table already exists.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            System.exit(1);
        }
    }


    public void SaveVariablesToMySQL() {

        //List<Object> workData = GetDataFromUser();
        List<Object> workData = Window.GetWorkList();

        String company = (String) workData.get(0);
        String topic = (String) workData.get(1);
        LocalDate today = (LocalDate) workData.get(2);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {

                String sql = "INSERT INTO works (Company, Topic, Date) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, company);
                    pstmt.setString(2, topic);
                    pstmt.setDate(3, java.sql.Date.valueOf(today));
                    pstmt.executeUpdate();
                    System.out.println("Variables saved successfully!\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GetDataFromDatabase() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");

                // Retrieve the variables from the table
                String sql = "SELECT id, Company, Topic, Date FROM works";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String company = rs.getString("Company");
                        String topic = rs.getString("Topic");
                        java.sql.Date date = rs.getDate("Date");

                        // Print the retrieved data
                        System.out.println("ID: " + id);
                        System.out.println("Company Name: " + company);
                        System.out.println("Topic: " + topic);
                        System.out.println("Date: " + date);
                        System.out.println("----------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void DeleteDataFromId(Integer id) {

        int idToDelete = id;
        // SQL DELETE statement
        String sql = "DELETE FROM works WHERE id = ?";

        // Establishing connection and deleting the record
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the ID parameter in the SQL statement
            statement.setInt(1, idToDelete);

            // Execute the DELETE statement
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A record was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DropTable() {
        // SQL statement to drop a table
        String dropTableSQL = "DROP TABLE IF EXISTS works";

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Execute the SQL statement
            statement.executeUpdate(dropTableSQL);
            System.out.println("Table dropped successfully");

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }

    }

    private static boolean tableExeists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        var resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }
}
